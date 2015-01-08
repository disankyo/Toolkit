package com.disankyo.remote.jms;

import com.disankyo.bean.BeanUtil;
import com.disankyo.remote.ServiceExporter;
import com.disankyo.util.MapUtil;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * 
 * @version 1.00 2012-3-30 11:37:07
 * @since 1.6
 * @author allean
 */
public class JMSServiceExporter extends ServiceExporter{

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Destination returnQueue;    //接收队列
    private Destination publishQueue;   //发布队列
    private Session receiveSession;     //接收和发送消息的上下文
    private MessageConsumer sendConsumer; //用于接收 发送到目的地消息的对象
    //执行线程池
    private final  ExecutorService threadPool = Executors.newCachedThreadPool();

    /**
     * 初始化，检查相应的资源是否已经准备完毕
     * @throws Exception 初始化异常
     */
    @Override
    protected void doInit() throws Exception {
        Exception ex;
        if(getConnectionFactory() == null){
            ex = new JMSException("JMS ConnectionFactory is empty.");
            getLog().errorLog(ex);
            throw ex;
        }

        if(getConnection() == null){
            ex = new JMSException("JMS Connection is empty.");
            getLog().errorLog(ex);
            throw ex;
        }

        if(getReturnQueue() == null){
            ex = new JMSException("Address of the queue return message is empty.");
            getLog().errorLog(ex);
            throw ex;
        }

        if(getPublishQueue() == null){
            ex = new JMSException("Address of the queue publish message is empty.");
            getLog().errorLog(ex);
            throw ex;
        }

        if(getTarget() == null){
            ex = new JMSException("Target object is empty.");
            getLog().errorLog(ex);
            throw ex;
        }

        if(getServiceName() == null){
            ex = new JMSException("ServiceName is empty.");
        }

        connection = getConnectionFactory().createConnection();
        //两个参数：一个表示会话是否是事务性的布尔和消息确认模式。
        receiveSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        sendConsumer = receiveSession.createConsumer(returnQueue);
        sendConsumer.setMessageListener(new MethodCallListener());
    }

    /**
     * 消息监听器，构造一个任务交给线程池执行
     */
    private class MethodCallListener implements MessageListener{

        /**
         * 收到消息调用的方法，
         * @param msg 请求的消息
         */
        public void onMessage(Message msg) {
            try {
                if (!msg.getJMSRedelivered()) {
                    if(ObjectMessage.class.isInstance(msg)){
                        String clientID = msg.getStringProperty(JMSConfig.SELF_INDENTITY);
                        if(clientID != null && !clientID.isEmpty()){
                            MethodCallTask task = new MethodCallTask(clientID);
                            task.setMessage((ObjectMessage) msg);
                            threadPool.submit(task);
                        }
                    }
                }
            } catch (JMSException ex) {
                getLog().errorLog(ex);
            }
        }

    }

    /**
     * 服务端响应客户端消息调用的处理单元，根据message消息来执行相应的方法，
     * 并把结果包装成ObjectMessage发送到客户端
     */
    @SuppressWarnings("unused")
    private class MethodCallTask implements Runnable{
        private ObjectMessage message;
        private String clientID;
         private Object[] emptyParam = new Object[0];
        public MethodCallTask(String clientID){
            this.clientID = clientID;
        }

		public ObjectMessage getMessage() {
            return message;
        }

        public void setMessage(ObjectMessage message) {
            this.message = message;
        }

        public void run() {
            Map<String, Serializable>
                    returnMap = new HashMap<String, Serializable>(
                    MapUtil.calculateMapInitialCapacity(2));
            Object value = null;
            //调用实际执行方法，返回调用结果
            try{
            value  = invoke();
            } catch (Exception ex) {
                handleException(returnMap, ex);
            }

            returnMap.put(JMSConfig.RETURN_VALUE, (Serializable) value);

            //发送结果到客户端
            sendReplyMessage(returnMap);
        }

        /**
         * 将结果包装成ObjectMessage对象发送到客户端
         * @param returnMap 
         */
        private void sendReplyMessage(Map<String, Serializable> returnMap){
            Session session = null;
            MessageProducer producer = null; //网目地的发消息的对象

            try {
                session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
                producer = session.createProducer(getReturnQueue());

                ObjectMessage replyMessage = session.createObjectMessage((Serializable) returnMap);
                replyMessage.setJMSCorrelationID(message.getJMSCorrelationID());
                replyMessage.setStringProperty(JMSConfig.SELF_INDENTITY, clientID);
                replyMessage.setStringProperty(JMSConfig.SERVICE_NAME, getServiceName());

                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                producer.send(replyMessage);
                
            } catch (JMSException ex) {
                getLog().errorLog(ex);
            }

        }

        /**
         * 根据消息信息，执行相应的方法
         * @return 执行方法的返回结果
         * @throws JMSException
         * @throws NoSuchMethodException
         * @throws IllegalAccessException
         * @throws IllegalArgumentException
         * @throws InvocationTargetException
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private Object invoke() 
                throws JMSException,
                NoSuchMethodException,
                IllegalAccessException,
                IllegalArgumentException,
                InvocationTargetException{
            Map<String, Object> callInfo = (Map<String, Object>) message.getObject();
            //获取客户端要调用的目标方法名称
            String methodName = (String) callInfo.get(JMSConfig.METHOD_NAME);
            //请求调用方法的参数类型
			Class[] paramClass = (Class[]) callInfo.get(JMSConfig.PARAMETER_CLASS_LIST);
            //请求调用方法的参数名称
            Object[] args = (Object[]) callInfo.get(JMSConfig.PARAMETER_VALUE_LIST);

            Object result = null;
            if(paramClass != null && args != null){
                if(paramClass.length == args.length){
                    result = BeanUtil.invokeMethod(
                            getTarget(), methodName, paramClass, args);
                    
                } else {
                    throw new NoSuchMethodException(
                            "Parameters and parameter values do not match.");
                }
            } else {
                result = BeanUtil.invokeMethod(
                        getTarget(), methodName, emptyParam);
            }
            
            return result;
        }

        /**
         * 异常的处理，如果抛出非JMS异常，则把异常信息发到客户端
         * @param returnMap 返回结果
         * @param ex 异常信息
         */
        private void handleException(Map<String, Serializable> returnMap, Exception ex){
            if(!JMSException.class.isInstance(ex)){
                if(InvocationTargetException.class.isInstance(ex)){
                    returnMap.put(JMSConfig.RETURN_EXCEPTION,
                            new Exception(((InvocationTargetException) ex).getTargetException().getMessage()));
                } else {
                    returnMap.put(JMSConfig.RETURN_EXCEPTION, new Exception(ex.getMessage()));
                }
            }

            getLog().errorLog(ex);
        }
    }

    /**
     * 执行相应的销毁操作
     * @throws Exception 销毁异常
     */
    @Override
    public void destory() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Destination getPublishQueue() {
        return publishQueue;
    }

    public void setPublishQueue(Destination publishQueue) {
        this.publishQueue = publishQueue;
    }

    public Session getReceiveSession() {
        return receiveSession;
    }

    public void setReceiveSession(Session receiveSession) {
        this.receiveSession = receiveSession;
    }

    public Destination getReturnQueue() {
        return returnQueue;
    }

    public void setReturnQueue(Destination returnQueue) {
        this.returnQueue = returnQueue;
    }

}
