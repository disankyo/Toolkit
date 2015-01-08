package com.disankyo.log.async;

import org.apache.commons.logging.Log;

import com.disankyo.log.LogUtil;
import com.disankyo.util.StringUtil;

/**
 * 异步日志记录工具类
 * @author xjx
 *
 */
public class AsyncLogUtil extends LogUtil{

    /**
     * 构造一个新的日志帮助实例。
     * @param logger 使用的日志记录器。
     */
    public AsyncLogUtil(final Log logger){
        this.logger = logger;
    }
    
    /**
     * 记录一个消息，消息中的参数将替换成params指定的值。
     * @param message 消息。
     * @param params 消息中的替换值。
     */
    public void infoLog(final String message, final Object... params) {
        checkEmptyMessage(message);
        LogExcutor.execute(new LogTask(logger, StringUtil.replaceArgs(message, params)));
    }

    /**
     * 记录一个debug消息，消息中的参数将替换成params指定的值。
     * @param message 消息。
     * @param params 消息中的替换值。
     */
    public void debugLog(String message, Object... params){
        checkEmptyMessage(message);

        LogExcutor.execute(new LogTask(logger, StringUtil.replaceArgs(message, params)));
    }
    
    /**
     *
     * 记录一个警告消息，消息中的参数将替换成params指定的值。
     * @param message 消息。
     * @param params 消息中的替换值。
     */
    public void warnLog(String message, Object... params){
        checkEmptyMessage(message);

        LogExcutor.execute(new LogTask(logger, StringUtil.replaceArgs(message, params)));
    }

    /**
     * 往日志中输出一个错误。错误和错误消息必须设置。
     * @param ex 错误实例。
     * @param message 错误消息。
     * @param params 消息中的替换值。
     */
    public void errorLog(Throwable ex, String message, Object... params){
        if (ex == null) {
            throw new IllegalArgumentException(
                    "Can not record the error message empty.");
        }

        if (message == null) {
            throw new IllegalArgumentException(
                    "Can not record the message blank.");
        }
        
        LogExcutor.execute(new LogTask(logger, StringUtil.replaceArgs(message, params)));
    }
    
}
