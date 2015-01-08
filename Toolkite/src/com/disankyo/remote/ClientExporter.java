package com.disankyo.remote;

import com.disankyo.proxy.factory.ProxyFactory;

import java.lang.reflect.InvocationHandler;

/**
 * 客户端生成代理对象的超类，子类需要实现invokeHandler方法来提供InvocationHandler实例
 * 超类已经定义了目标接口的注入和获取方法。
 * @version 1.00 2012-3-27 17:51:57
 * @since 1.6
 * @author allean
 */
@SuppressWarnings("rawtypes")
public abstract class ClientExporter  extends Exporter{

    private Class interfaceClass;

    /**
     * 获取接口信息
     * @return 接口信息
     */
    public Class getInterfaceClass() {
        return interfaceClass;
    }

    /**
     * 设置接口信息
     * @param interfaceClass 接口信息
     */
    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    /**
     * 构造目标对象的代理实例
     * @return 目标对象的代理实例
     */
    public Object getProxyObject(){
        if(isReady()){
            InvocationHandler handler = invokeHandler();
            return ProxyFactory.createInvokerProxy(interfaceClass, handler);
        } else {
            throw new IllegalStateException("Client has not been properly initializated.");
        }
    }

    /**
     * 构造代理实例，由子类实现
     * @return 代理实例
     */
    protected abstract InvocationHandler invokeHandler();

}
