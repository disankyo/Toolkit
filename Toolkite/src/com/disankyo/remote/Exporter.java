package com.disankyo.remote;

import com.disankyo.log.SyncLogUtil;
import com.disankyo.serialize.SerializeStrategy;

import org.apache.commons.logging.LogFactory;

/**
 * 远程工厂方法调用超类
 * @version 1.00 2012-3-27 17:23:29
 * @since 1.6
 * @author allean
 */
public abstract class Exporter {

    /**
     * 日志记录器
     */
    private static final SyncLogUtil LOG = new SyncLogUtil(LogFactory.getLog(Exporter.class));
    /**
     * 是否已经初始化的标记，默认为false
     */
    private static volatile boolean initialization = false;
    /**
     * 序列化策略实现
     */
    private SerializeStrategy serializeStrategy = SerializeStrategy.getInstance();

    /**
     * 返回当前是否已经初始化完成
     * @return <code>true</code>完成 <code>false</code>未完成
     */
    public boolean isReady(){
        return initialization;
    }

    /**
     * 获取数据的序列化策略实现
     * @return 序列化策略实现
     */
    public SerializeStrategy getSerializeStrategy() {
        return serializeStrategy;
    }

    /**
     * 设置数据的序列化策略实现
     * @param serializeStrategy 序列化策略实现
     */
    public void setSerializeStrategy(SerializeStrategy serializeStrategy) {
        this.serializeStrategy = serializeStrategy;
    }

    /**
     * 初始化
     * @return 初始化异常
     */
    public void init() throws Exception{

        if(!initialization){
            doInit();
            initialization = true;
        }
    }

    /**
     * 获取日志记录对象
     * @return 日志记录对象
     */
    protected static SyncLogUtil getLog(){
        return LOG;
    }
    
    /**
     * 需要子类实现的执行对应的初始化方法
     * @throws Exception 初始化异常
     */
    protected abstract void doInit() throws Exception;

    /**
     * 需要子类实现的执行对应的销毁方法
     * @return 销毁异常
     */
    public abstract void destory() throws Exception;
}
