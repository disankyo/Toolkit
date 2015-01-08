package com.disankyo.proxy.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 
 * @version 1.00 2012-3-28 11:13:20
 * @since 1.6
 * @author allean
 */
@SuppressWarnings("rawtypes")
public class ProxyFactory {

    /**
     * 使用指定的类加载器，创建给定接口的代理实例
     * @param classLoader 生成代理对象时使用的类加载器。
     * @param interfaces 代理对象实现的接口的类型
     * @param invoker 方法调用器
     * @return 实现给定接口的代理实例
     */
    public static Object createInvokerProxy(
    		ClassLoader classLoader, Class interfaceType, InvocationHandler handler){
        return Proxy.newProxyInstance(classLoader, new Class[]{interfaceType}, handler);
    }

    /**
     * 使用指定的类加载器，创建给定接口的代理实例
     * @param classLoader 生成代理对象时使用的类加载器。
     * @param interfaces 代理对象实现的接口的类型
     * @param invoker 方法调用器
     * @return 实现给定接口的代理实例
     */
    public static Object createInvokerProxy(
    		Class interfaceType, InvocationHandler handler){
        return createInvokerProxy(
                interfaceType.getClassLoader(),
                interfaceType,
                handler);
    }
    
}
