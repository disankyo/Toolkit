package com.disankyo.log;

import org.apache.commons.logging.LogFactory;

import com.disankyo.log.async.AsyncLogUtil;

/**
 * 生成记录日志的日志工厂类 查找资源"/log4j.properties".
 * 不依赖于log4j的实现的日志记录器
 * @author xjx
 *
 */
public class LoggerFactory {

	/**
     * 为clazz创建一个日志实例
     * @param clazz 具体的类
     * @return LogUtil 实例
     */
	@SuppressWarnings("unchecked")
	public static SyncLogUtil getLog(Class clazz){
        return new SyncLogUtil(LogFactory.getLog(clazz));
    }
	
	/**
     * 为clazz创建一个异步日志实例
     * @param clazz 具体的类
     * @return AsynchLogUtil 实例
     */
	@SuppressWarnings("unchecked")
	public static AsyncLogUtil getAsynchLog(Class clazz){
        return new AsyncLogUtil(LogFactory.getLog(clazz));
    }
	
	/**
     * 为clazz创建一个异步日志实例
     * @param name 具体的类
     * @return AsynchLogUtil 实例
     */
	@SuppressWarnings("unchecked")
	public static AsyncLogUtil getAsynchLog(String name){
        return new AsyncLogUtil(LogFactory.getLog(name));
    }
}
