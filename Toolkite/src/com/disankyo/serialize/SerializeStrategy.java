package com.disankyo.serialize;

import java.io.IOException;

import com.disankyo.util.ByteUtil;


/**
 * 默认序列化接口的实现
 * @version 1.00 2012-4-6 10:27:20
 * @since 1.6
 * @author allean
 */
public class SerializeStrategy implements Serialize{

    private static SerializeStrategy SERIALIZE = new SerializeStrategy();
    private SerializeStrategy(){
    }

    public static SerializeStrategy getInstance(){
        return SERIALIZE;
    }
    
    @Override
    public byte[] serialize(Object obj) {
    	
    	try {
			return ByteUtil.objectToByte(obj);
		} catch (IOException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Not supported yet.");
		}
    }

    @Override
    public Object unSerialize(byte[] datas) {
    	try {
			return ByteUtil.byteToObject(datas);
		} catch (IOException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Not supported yet.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Not supported yet.");
		}
    }

}
