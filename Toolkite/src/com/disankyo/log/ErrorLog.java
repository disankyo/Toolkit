package com.disankyo.log;

/**
 * 工具包中错误日志记录器
 * @author xjx
 *
 */
public class ErrorLog {

	private static final LogUtil LOG = LoggerFactory.getAsynchLog(ErrorLog.class);
	
	public static void error(Throwable e, String message, Object...params){
		LOG.errorLog(e, message, params);
	}
}
