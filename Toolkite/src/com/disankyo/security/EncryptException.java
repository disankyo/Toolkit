package com.disankyo.security;

/**
 *  不能对数据加密抛出异常
 * @version 1.00 2012-5-22 12:11:42
 * @since 1.6
 * @author allean
 */
public class EncryptException extends Exception{

	private static final long serialVersionUID = -7732688218342744075L;

	public EncryptException(){
    }

    public EncryptException(String message){
        super(message);
    }

    public EncryptException(Throwable cause){
        super(cause);
    }

    public EncryptException(String message,Throwable cause){
        super(message, cause);
    }
}
