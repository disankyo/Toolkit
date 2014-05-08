package com.disankyo.log;

import org.apache.commons.logging.Log;

import com.disankyo.util.StringUtil;

/**
 * 同步日志记录工具类
 * @version 1.00 2010-12-26 21:48:51
 * @since 1.6
 * @author disankyo
 */
public class SyncLogUtil extends LogUtil{
    
    /**
     * 构造一个新的日志帮助实例。
     * @param logger 使用的日志记录器。
     */
    public SyncLogUtil(final Log logger){
        this.logger = logger;
    }
    
    /**
     * 记录一个消息，消息中的参数将替换成params指定的值。
     * @param message 消息。
     * @param params 消息中的替换值。
     */
    public void infoLog(String message, Object... params) {
        checkEmptyMessage(message);

        if (logger != null && logger.isInfoEnabled()) {
            logger.info(StringUtil.replaceArgs(message, params));
        }
    }
    
    /**
     * 记录一个debug消息，消息中的参数将替换成params指定的值。
     * @param message 消息。
     * @param params 消息中的替换值。
     */
    public void debugLog(String message, Object... params){
        checkEmptyMessage(message);

        if (logger != null && logger.isDebugEnabled()) {
            logger.debug(StringUtil.replaceArgs(message, params));
        }
    }
    
    /**
     *
     * 记录一个警告消息，消息中的参数将替换成params指定的值。
     * @param message 消息。
     * @param params 消息中的替换值。
     */
    public void warnLog(String message,Object... params){
        checkEmptyMessage(message);

        if (logger != null && logger.isWarnEnabled()) {
            logger.warn(StringUtil.replaceArgs(message, params));
        }
    }

    /**
     * 往日志中输出一个错误。错误和错误消息必须设置。
     * @param ex 错误实例。
     * @param message 错误消息。
     * @param params 消息中的替换值。
     */
    public void errorLog(Throwable ex,String message,Object... params){
        if (ex == null) {
            throw new IllegalArgumentException(
                    "Can not record the error message empty.");
        }

        if (message == null) {
            throw new IllegalArgumentException(
                    "Can not record the message blank.");
        }

        if(logger != null && logger.isErrorEnabled()){
            logger.error(StringUtil.replaceArgs(message, params),ex);
        }
    }

}

