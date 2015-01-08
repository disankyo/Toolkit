package com.disankyo.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用于Bean之间的属性复制
 * @version 1.00 2012-3-30 16:12:09
 * @since 1.6
 * @author allean
 */
@SuppressWarnings("rawtypes") 
public class BeanUtil {

    /**
     * 根据指定方法的参数类型，执行指定对象的方法
     * @param target 目标对象
     * @param methodName 方法名称
     * @param clazzs 方法的参数类型
     * @param param 方法的参数值
     * @return 方法执行的结果
     * @throws NoSuchMethodException 找不到匹配的方法
     * @throws IllegalAccessException 方法不可访问
     * @throws IllegalArgumentException 如果该方法是实例方法，且指定对象参数不是声明底层方
     * 法的类或接口（或其中的子类或实现程序）的实例；如果实参和形参的数量不相同；
     * 如果基本参数的解包转换失败；如果在解包后，无法通过方法调用转换将参数值转换为相应的形参类型。
     * @throws InvocationTargetException 底层方法执行过程产生异常
     */
	public static Object invokeMethod(
            Object target,
            String methodName,
            Class[] clazzs,
            Object[] param)
            throws NoSuchMethodException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException{
        Method method = findMethod(target, methodName, clazzs);
        
        return method.invoke(target, param);
    }

    /**
     * 执行指定对象的方法，会推断对应的类型
     * @param target 目标对象
     * @param methodName 方法名称
     * @param param 方法的参数值
     * @return 方法执行的结果
     * @throws NoSuchMethodException 找不到匹配的方法
     * @throws IllegalAccessException 方法不可访问
     * @throws IllegalArgumentException 如果该方法是实例方法，且指定对象参数不是声明底层方
     * 法的类或接口（或其中的子类或实现程序）的实例；如果实参和形参的数量不相同；
     * 如果基本参数的解包转换失败；如果在解包后，无法通过方法调用转换将参数值转换为相应的形参类型。
     * @throws InvocationTargetException 底层方法抛出的异常
     */
    public static Object invokeMethod(
            Object target,
            String methodName,
            Object[] param)
            throws NoSuchMethodException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException{
        Class[] clazzs = new Class[param.length];
        for(int num = 0; num < clazzs.length; num++){
            clazzs[num] = param[num].getClass();
        }
        return invokeMethod(target, methodName, clazzs, param);
    }
    
    /**
     * 获取指定方法的调用
     * @param obj 目标对象
     * @param name 方法名称
     * @param clazzs 方法的参数类型
     * @return 方法的信息
     * @throws NoSuchMethodException
     */
    private static Method findMethod(
            Object obj,
            String name,
            Class[] clazzs)
            throws NoSuchMethodException{
        return obj.getClass().getMethod(name, clazzs);
    }
}

