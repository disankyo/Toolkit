package com.disankyo.serialize;

/**
 * 序列化的策略接口
 * @version 1.00 2012-4-6 10:18:52
 * @since 1.6
 * @author allean
 */
public interface Serialize {

    public byte[] serialize(Object obj);

    public Object unSerialize(byte[] datas);
}
