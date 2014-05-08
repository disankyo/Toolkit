package com.disankyo.net;

import java.net.Socket;

/**
 * 从连接池中获取套接字(Socket)的接口
 * @version 1.00 2012-4-1 11:18:39
 * @since 1.6
 * @author allean
 */
public interface SocketSource {

    /**
     * 实际从连接池中获取套接字的方法
     * @return 套接字
     * @throws Exception java.lang.Exception
     */
    public Socket getSocket() throws Exception;
}
