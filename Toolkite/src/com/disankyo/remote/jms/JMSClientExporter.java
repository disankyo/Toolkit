package com.disankyo.remote.jms;

import com.disankyo.id.IdGenerate;
import com.disankyo.remote.ClientExporter;
import com.disankyo.remote.jms.listener.MessageReceiveListener;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * 利用JMS远程调用客户端的实现。
 * 使用时必须先通过register方法注册需要响应的消息，注册返回一个消息探测器MessageProbe对象
 * 调用者只需要调用await方法阻塞以等待响应数据的到来。
 * 流程：
 * 首先构造ClientExporter并初始化
 * 调用register注册响应消息得到一个MessageProbe实例
 * 调用await获取数据
 * 调用unRegister取消注册
 * @version 1.00 2012-3-28 10:11:31
 * @since 1.6
 * @author allean
 */
public class JMSClientExporter extends ClientExporter {

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Destination publishQueue;     //发布队列
    private Destination returnQueue;      //接收队列
    private String serviceName;           //服务名称
    private Session receiveSession;       //用于接收和发送消息的上下文
    private MessageConsumer sendConsumer; //用于接收 发送到目的地消息的对象
    private final String selfIdentity = IdGenerate.getUUIDString();//客户端身份标识
    //回复消息池，用于暂存回复的消息。
    private final Map<String, MessageProbe> registerPool =
            new ConcurrentHashMap<String, MessageProbe>();

    /**
     * 初始化方法
     * @throws Exception
     */
    @Override
    protected void doInit() throws Exception {
        Exception ex;
        if (getConnectionFactory() == null) {
            ex = new JMSException("JMS ConnectionFactory is empty.");
            getLog().errorLog(ex);
            throw ex;
        }

        if (getConnection() == null) {
            ex = new JMSException("JMS Connection is empty.");
            getLog().errorLog(ex);
            throw ex;
        }

        if (getPublishQueue() == null) {
            ex = new JMSException(
                    "Address of the queue publich messages is empty.");
            getLog().errorLog(ex);
            throw ex;
        }

        if (getReturnQueue() == null) {
            ex = new JMSException("Address of queue return messages is empty.");
            getLog().errorLog(ex);
            throw ex;
        }

        if (getServiceName() == null) {
            ex = new JMSException("Service name is not legitimate,{" +
                    getServiceName() + "}");
            getLog().errorLog(ex);
            throw ex;
        }

        if (getInterfaceClass() == null) {
            ex =
                    new JMSException(
                    "Must specify the objective of the interface.");
            getLog().errorLog(ex);
            throw ex;
        }

        connection = getConnectionFactory().createConnection();
        //两个参数：一个表示会话是否是事务性的布尔和消息确认模式。
        receiveSession = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        sendConsumer = receiveSession.createConsumer(returnQueue);
        sendConsumer.setMessageListener(new MessageReceiveListener(registerPool));
    }

    @Override
    public void destory() throws Exception {
        if (sendConsumer != null) {
            sendConsumer.close();
        }

        if (receiveSession != null) {
            receiveSession.close();
        }

        if (connection != null) {
            connection.close();
        }

        registerPool.clear();
    }

    @Override
    protected InvocationHandler invokeHandler() {
        return new MethodCallClientInvocationHandler();
    }

    /**
     * 一个代理实例，用于拦截对于指定接口的方法调用
     * 将消息转换成JMS消息调用，并获取远程调用的返回值
     */
    private class MethodCallClientInvocationHandler implements InvocationHandler {

        /**
         * 实际的拦截方法，每次调用目标对象方法都会被此方法拦截，
         * 转换成JMS调用，并返回远程方法的返回值。
         * @param proxy 代理类
         * @param method 调用的方法
         * @param args 方法的参数
         * @return 远程调用的返回值
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws
                Throwable {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //客户端要发送的调用消息信息
            ObjectMessage sendMessage = buildMessage(session, method, args);
            // 注册消息
            MessageProbe probe = register(sendMessage.getJMSCorrelationID());

            //发送消息
            sendMessage(session, sendMessage);

            //接收消息
            Map<String, Serializable> returnCollection =
                    receiveMessage(sendMessage.getJMSCorrelationID(), probe);

            Exception returnException = (Exception) returnCollection.get(JMSConfig.RETURN_EXCEPTION);

            Object returnValue = returnCollection.get(JMSConfig.RETURN_VALUE);

            if(returnException != null){
                throw returnException;
            } else {
                return returnValue;
            }
        }

        /**
         * 接收服务端的回复
         * @param messageID 消息的链接ID
         * @param probe 探测器
         * @return 服务端发送的结果
         */
        @SuppressWarnings("unused")
		private Map<String, Serializable> receiveMessage(
                String messageID,
                MessageProbe probe) 
                throws InterruptedException, TimeoutException {
            Map<String, Serializable> returnValue = null;

            try {
                if (JMSConfig.RECEIVE_TIMEOUT > 0) {
                    returnValue = probe.await(JMSConfig.RECEIVE_TIMEOUT);
                } else {
                    returnValue = probe.await();
                }
            } catch (InterruptedException ex) {
                throw ex;
            } catch (TimeoutException ex){
                throw ex;
            } finally {
                unRegister(messageID);
            }

            if(returnValue == null){
                return Collections.emptyMap();
            } else {
                return returnValue;
            }
            
        }

        /**
         * 发送消息
         * @param session
         * @param sendMessage
         */
        private void sendMessage(
                Session session,
                ObjectMessage sendMessage)
                throws JMSException {
            MessageProducer producer = null;
            producer = session.createProducer(getReturnQueue());
            //发送模式，设置不持久
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            producer.send(sendMessage);
        }

        /**
         * 根据指定的方法和参数，
         * @param session
         * @param method
         * @param args
         * @return
         */
        @SuppressWarnings("rawtypes")
        private ObjectMessage buildMessage(
                Session session,
                Method method,
                Object[] args)
                throws JMSException {

            String methodName = method.getName();

            Map<String, Serializable> callInfo =
                    new HashMap<String, Serializable>();
            callInfo.put(JMSConfig.METHOD_NAME, methodName);

           
			Class[] paramsClass = method.getParameterTypes();

            if (paramsClass.length > 0) {
                callInfo.put(JMSConfig.PARAMETER_VALUE_LIST, args);
                callInfo.put(JMSConfig.PARAMETER_CLASS_LIST, paramsClass);
            }


            ObjectMessage sendMessage = session.createObjectMessage(
                    (Serializable) callInfo);
            sendMessage.setJMSCorrelationID(IdGenerate.getUUIDString());
            sendMessage.setStringProperty(JMSConfig.SELF_INDENTITY, selfIdentity);
            sendMessage.setStringProperty(JMSConfig.SERVICE_NAME, serviceName);

            return sendMessage;
        }
    }

    /**
     * 绑定指定Id的消息
     * @param JMSCorrelationID 消息的Id
     * @return
     */
    private MessageProbe register(String JMSCorrelationID) {
        checkStatus();
        if (JMSCorrelationID == null || JMSCorrelationID.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        MessageProbe probe = new MessageProbe();
        registerPool.put(JMSCorrelationID, probe);
        return probe;
    }

    /**
     * 放弃接收消息
     * @param messageID 消息链接ID
     */
    private void unRegister(String messageID) {
        if (messageID != null && !messageID.isEmpty()) {
            if (registerPool.containsKey(messageID)) {
                registerPool.remove(messageID);
            }
        }
    }

    /**
     * 检查是否为已经准备好的状态
     */
    private void checkStatus() {
        if (!isReady()) {
            throw new IllegalStateException(
                    "Client has not been properly initialized.");
        }
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

    public Destination getReturnQueue() {
        return returnQueue;
    }

    public void setReturnQueue(Destination returnQueue) {
        this.returnQueue = returnQueue;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
