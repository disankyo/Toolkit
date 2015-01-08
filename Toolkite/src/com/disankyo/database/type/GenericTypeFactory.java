package com.disankyo.database.type;

/**
 * 数据类型的静态工厂类
 * @version 1.00 2010-12-30 15:10:38
 * @since 1.6
 * @author disankyo
 */
public class GenericTypeFactory implements TypeFactory{

    public <TYPE> Type<TYPE> createType(String classNname) throws ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SuppressWarnings("rawtypes")
    public <TYPE> Type<TYPE> createType(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
