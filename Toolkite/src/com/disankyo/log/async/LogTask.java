package com.disankyo.log.async;

import org.apache.commons.logging.Log;

/**
 * 异步执行日志记录的线程
 * @author xjx
 *
 */
public class LogTask implements Runnable{
	
	private Log logger;
	private String message;
	public LogTask(Log logger, String message){
		this.logger = logger;
		this.message = message;
	}

	@Override
	public void run() {
		if(logger != null){
			if(logger.isInfoEnabled()){
				logger.info(message);
			} else if(logger.isDebugEnabled()){
				logger.debug(message);
			} else if(logger.isErrorEnabled()){
				logger.error(message);
			} else if(logger.isWarnEnabled()){
				logger.debug(message);
			}
		}
	}

}
