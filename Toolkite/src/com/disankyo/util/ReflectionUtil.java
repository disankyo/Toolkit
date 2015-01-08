package com.disankyo.util;

import java.lang.reflect.Method;

/**
 * 反射工具类
 * @author xjx
 * @version 2014-7-15 上午09:46:59
 */
public class ReflectionUtil {

	/**
	 * 通过反射机制 调用某类的方法
	 * @param clazz
	 * @param methodName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Object clazz, String methodName, Object[] params) throws Exception{
		
		Method[] methods = clazz.getClass().getMethods();
		
		for (Method method : methods) {
			if(method.getName().equals(methodName)){
				
				Class<?>[] paramClassTypes = method.getParameterTypes();
				if(params.length == paramClassTypes.length){
					return method.invoke(clazz, params);
				}
			}
		}
		
		throw new Exception((new StringBuilder(methodName)).append("'s  parameter error !").toString());
	}
	
	/**
	 * 根据类全名 获取类实例
	 * @param className 类全名
	 * @return
	 * @throws Exception
	 */
	public static Object invokeInstance(String className) throws Exception{
		Class<?> classes = Class.forName(className);
		
		return classes.newInstance();
	}
}
