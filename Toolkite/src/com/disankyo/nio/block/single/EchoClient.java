package com.disankyo.nio.block.single;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * 基于nio的通信服客户端
 * @author xujianxin
 * @time Oct 14, 2013 1:20:12 PM
 */
public class EchoClient {

	private static final int PORT = 8000;
	private static final String SERVER = "localhost";
	private InetSocketAddress inetSocketAddress;
	private SocketChannel socketChannel = null;
	
	public EchoClient() throws IOException{
		socketChannel = SocketChannel.open();
		
		inetSocketAddress = new InetSocketAddress(SERVER, PORT);
		socketChannel.connect(inetSocketAddress);
	}
	
	public void service(){
		Socket socket = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		
		BufferedReader localIn = null;
		while(true){
			try {
				socket = socketChannel.socket();
				reader = getReader(socket);
				writer = getWriter(socket);
				
				localIn = new BufferedReader(new InputStreamReader(System.in));
				
				String msg = null;
				while((msg = localIn.readLine()) != null){
					writer.println(msg);
					
					System.out.println("server back : " + reader.readLine());
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
		new EchoClient().service();
	}
}
