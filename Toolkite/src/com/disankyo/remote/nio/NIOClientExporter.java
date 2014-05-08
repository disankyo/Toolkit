package com.disankyo.remote.nio;

import com.disankyo.net.SocketSource;
import com.disankyo.remote.ClientExporter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * NIO远程方法调用客户端
 * @version 1.00 2012-3-30 18:00:28
 * @since 1.6
 * @author allean
 */
public class NIOClientExporter extends ClientExporter{

    /**
     * 连接池
     */
    private SocketSource socketSource;
    /**
     * 远程调用服务的名称
     */
    private String serviceName;
    /**
     * 方法拦截器
     */
    private InvocationHandler handler;
    /**
     * 初始化构造拦截器
     */
    public NIOClientExporter(){
        handler = new NIOSocketClientInvokerHandler();
    }

    /**
     * 代理实例
     */
    private class NIOSocketClientInvokerHandler implements InvocationHandler{

        /**
         * 转化成目标方法的远程调用
         * @param proxy 目标
         * @param method 方法
         * @param args 参数类型
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MethodData info = new MethodData(method.getName(), args, method.getParameterTypes(), serviceName);

            byte[] data = NIOClientExporter.this.getSerializeStrategy().serialize(info);

            Socket socket = socketSource.getSocket();
            //发送请求调用远程信息
            send(socket, data);

            Object retuanValue = receive(socket);
            
           return retuanValue;
        }

    }

    /**
     * 发送远程调用的请求
     * @param socket Socket套接字
     * @param data 当前连接
     * @throws IOException
     */
    private void send(Socket socket, byte[] data)
            throws IOException{
        OutputStream out = socket.getOutputStream();
        out.write(data);
        out.flush();
    }

    /**
     * 接收远程方法调用
     * @param socket 当前连接
     * @return 远程方法返回结果
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Object receive(Socket socket)
            throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        try{
            return in.readObject();
        } finally {
            in.close();
        }
    }

    /**
     * 获取服务名称
     * @return 服务名称
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 设置服务名称
     * @param serviceName 服务名称
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 获取连接池
     * @return 连接池
     */
    public SocketSource getSocketSource() {
        return socketSource;
    }

    /**
     * 设置连接池
     * @param soketSource 连接池
     */
    public void setSocketSource(SocketSource socketSource) {
        this.socketSource = socketSource;
    }

    /**
     * 返回接口的代理实例
     * @return 代理实例
     */
    @Override
    protected InvocationHandler invokeHandler() {
        return handler;
    }

    @Override
    protected void doInit() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destory() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
