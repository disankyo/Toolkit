package com.disankyo.database.type;

/**
 * 数据类型的工厂
 * @version 1.00 2010-12-30 14:46:39
 * @since 1.6
 * @author disankyo
 */
public interface TypeFactory {

    /**
     * 创建Type
     * @param <TYPE> 数据类型
     * @param name 完整Class类名
     * @return Type实例
     * @throws ClassNotFoundException
     */
    public <TYPE> Type <TYPE> createType(String classNname) throws ClassNotFoundException;

    /**
     * 创建Type
     * @param <TYPE> 数据类型
     * @param clazz 类型实例
     * @return Type实例
     */
    @SuppressWarnings("rawtypes")
    public <TYPE> Type <TYPE> createType(Class clazz);
}
