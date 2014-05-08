package com.disankyo.nio.block.single;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 基于nio阻塞的通信服服务端 单线程
 * @author xujianxin
 * @time Oct 14, 2013 1:20:22 PM
 */
public class EchoServer {

	private static final int PORT = 8000;
	private ServerSocketChannel serverSocketChannel;
	private ServerSocket serverSocket;
	private SocketChannel socketChannel;
	
	public EchoServer() throws IOException{
		serverSocketChannel = ServerSocketChannel.open();
		serverSocket = serverSocketChannel.socket();
		
		serverSocket.setReuseAddress(true);
		serverSocket.bind(new InetSocketAddress(PORT));
	}
	
	public void service(){
		BufferedReader reader = null;
		PrintWriter writer = null;
		Socket socket = null;
		while (true) {
			try {
				socketChannel = serverSocketChannel.accept();
				socket = socketChannel.socket();
				
				reader = getReader(socket);
				writer = getWriter(socket);
				
				String msg = null;
				while((msg = reader.readLine()) != null){
					writer.println(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(socketChannel != null){
					try {
						socketChannel.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	private BufferedReader getReader(Socket socket) throws IOException{
		InputStream in = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(in));
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException{
		OutputStream out = socket.getOutputStream();
		return new PrintWriter(out, true);
	}
	
	public static void main(String[] args) throws IOException {
		new EchoServer().service();
	}
	
	
}
