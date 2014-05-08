package com.disankyo.database.type.support;

import com.disankyo.database.type.Type;

/**
 * 默认的数据类型
 * @version 1.00 2010-12-30 14:45:46
 * @since 1.6
 * @author disankyo
 */
public abstract class BaseType<T> implements Type<T> {

    @Override
    public boolean equals(Object obj) {
        return this.getTypeClass().getName().equals(obj.getClass().getName());
    }

    @Override
    public int hashCode() {
        return this.getTypeClass().getName().hashCode();
    }
    
    
}
