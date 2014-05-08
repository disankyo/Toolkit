package com.disankyo.net.support;

import com.disankyo.net.SocketValidate;

import java.net.Socket;

/**
 * 一个默认的Socket的检查实现
 * @version 1.00 2012-4-5 17:33:23
 * @since 1.6
 * @author allean
 */
public class DefaultSocketValidate implements SocketValidate{

    /**
     * 
     * @param socket Socket实例
     * @return true可以提供服务，false不能提供服务
     */
    @Override
    public boolean validate(Socket socket) {
        if(socket == null){
            return false;
        }

        if(socket.isClosed()
                || !socket.isConnected()
                || socket.isInputShutdown()
                || socket.isOutputShutdown()){
            return false;
        } else {
            return true;
        }
    }

}
