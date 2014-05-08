package com.disankyo.log;

import org.apache.commons.logging.Log;

public abstract class LogUtil {

	public Log logger; //日志记录器。
    private static Object[] empty = null;
    
    /**
     * 记录一个无参数的消息。
     * @param message 消息。
     */
    public void infoLog(String message) {
    	infoLog(message, empty);
    }
    
    /**
     * 记录一个消息，消息中的参数将替换成params指定的值。
     * @param message 消息。
     * @param params 消息中的替换值。
     */
    public abstract void infoLog(final String message, final Object... params);
    
    /**
     * 记录一个debug消息。
     * @param message 消息。
     */
    public void debugLog(String message){
        debugLog(message, empty);
    }
    
    /**
     * 记录一个debug消息，消息中的参数将替换成params指定的值。
     * @param message 消息。
     * @param params 消息中的替换值。
     */
    public abstract void debugLog(String message, Object... params);
    
    /**
     * 记录一个警告信息。
     * @param message 消息
     */
    public void warnLog(String message){
        warnLog(message, empty);
    }
    
    /**
    *
    * 记录一个警告消息，消息中的参数将替换成params指定的值。
    * @param message 消息。
    * @param params 消息中的替换值。
    */
   public abstract void warnLog(String message,Object... params);
    
    /**
     * 记录一个报错信息
     * @param ex 错误信息
     */
    public void errorLog(Throwable ex){
        errorLog(ex, ex.getMessage() == null ? "" : ex.getMessage(), empty);
    }
    
    /**
     * 往日志中输出一个错误。错误和错误消息必须设置。
     * @param ex 错误实例。
     * @param message 错误消息。
     * @param params 消息中的替换值。
     */
    public abstract void errorLog(Throwable ex,String message,Object... params);
    
    /**
     * 参数为空的话抛出异常
     * @param message
     */
    public void checkEmptyMessage(String message) {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException(
                    "Can not record the message blank.");
        }
    }
}
