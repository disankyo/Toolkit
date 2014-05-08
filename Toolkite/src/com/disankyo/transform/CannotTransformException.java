package com.disankyo.transform;

/**
 * 无法转换的异常
 * @version 1.00 2012-4-18 17:13:45
 * @since 1.6
 * @author allean
 */
public class CannotTransformException extends Exception{
    
	private static final long serialVersionUID = 4800198661121946291L;

	public CannotTransformException(){}
    
    public CannotTransformException(Throwable cause){
        super(cause);
    }

    public CannotTransformException(String message){
        super(message);
    }

    public CannotTransformException(String message, Throwable cause){
        super(message, cause);
    }
}
