package com.disankyo.net;

import java.net.Socket;

/**
 * 针对Socket进行验证的接口
 * @version 1.00 2012-4-5 17:30:08
 * @since 1.6
 * @author disankyo
 */
public interface SocketValidate {

    /**
     * 验证Socket实例是否能真实提供服务
     * @param socket Socket实例
     * @return 返回true可以提供服务，反之不能。
     */
    public boolean validate(Socket socket);

}
