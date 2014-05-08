package com.disankyo.remote.jms;

/**
 * 
 * @version 1.00 2012-3-28 15:03:19
 * @since 1.6
 * @author allean
 */
public class JMSConfig {

    /**
     * 需要调用的方法名称
     */
    public static final String METHOD_NAME = "METHOD_NAME";
    /**
     * 消息调用的参数名称
     */
    public static final String PARAMETER_VALUE_LIST = "PARAMETER_VALUE_LIST";
    /**
     * 消息调用的方法参数类型
     */
    public static final String PARAMETER_CLASS_LIST = "PARAMETER_CLASS_LIST";
    /**
     * 消息选择器名称
     */
    public static final String PARAMETER_MESSAGE_KEY = "ACTION";
    /**
     * 客户端自身标识符
     */
    public static final String SELF_INDENTITY = "SELF_INDENTITY";
    /**
     * 服务名称
     */
     public static final String SERVICE_NAME = "SERVICE_NAME";
     /**
      * 接收消息的超时时间，单位毫秒
      */
     public static final long RECEIVE_TIMEOUT = 60000L;
     /**
      * 远程方法调用异常
      */
      public static final String RETURN_EXCEPTION = "RETURN_EXCEPTION";
      /**
       * 远程方法调用返回值
       */
      public static final String RETURN_VALUE = "RETURN_VALUE";
}
