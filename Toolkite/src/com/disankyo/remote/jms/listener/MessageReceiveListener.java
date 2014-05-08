package com.disankyo.remote.jms.listener;

import com.disankyo.log.SyncLogUtil;
import com.disankyo.remote.jms.MessageProbe;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.commons.logging.LogFactory;

/**
 * 接收服务端消息的监听器，只会将类型为ObjectMessage和有JMSCorrelationId属性
 * 并且此JMSCorrelationID已经注册的消息放入消息池。
 * @version 1.00 2012-3-28 12:44:19
 * @since 1.6
 * @author allean
 */
public class MessageReceiveListener implements MessageListener {
    private static final SyncLogUtil LOG =
            new SyncLogUtil(LogFactory.getLog(MessageReceiveListener.class));
    private Map<String, MessageProbe> registerPool;

    public MessageReceiveListener(Map<String, MessageProbe> registerPool){
        this.registerPool = registerPool;
    }
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        try {
            String jmsId = message.getJMSCorrelationID();

            if(jmsId != null && ObjectMessage.class.isInstance(message)){
                ObjectMessage objMessage = (ObjectMessage) message;

                Object returnObject = objMessage.getObject();
                if(returnObject != null && Map.class.isInstance(returnObject)){
                    Map<String, Serializable> returnMap = (Map<String, Serializable>) objMessage.getObject();
                    //如果消息被注册，将设置调用结果并中断调用线程的阻塞
                    if(returnMap.containsKey(jmsId)){
                        MessageProbe probe = registerPool.get(jmsId);
                        if(returnMap == null){
                            returnMap = Collections.emptyMap();
                        }
                        probe.obtainReturnValue(returnMap);
                    } else {
                        LOG.debugLog("Message {0} not register, so give the message.", jmsId);
                    }
                } else {
                    LOG.errorLog(new Exception("The wrong response, the server return the result is not compatible."));
                }
            } else {
                LOG.errorLog(new Exception("The wrong response, the response server message type is not compatible."));
            }
        } catch (JMSException ex) {
            LOG.errorLog(ex);
        }
        
    }

}
