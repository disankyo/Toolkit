package com.disankyo.security;

/**
 * 不能对数据解密抛出异常
 * @version 1.00 2012-5-22 12:16:54
 * @since 1.6
 * @author allean
 */
public class DecryptException extends Exception{

	private static final long serialVersionUID = 3992088596297412077L;

	public DecryptException(){

    }

    public DecryptException(String message){
        super(message);
    }

    public DecryptException(Throwable cause){
        super(cause);
    }

    public DecryptException(String message, Throwable cause){
        super(message, cause);
    }
}
