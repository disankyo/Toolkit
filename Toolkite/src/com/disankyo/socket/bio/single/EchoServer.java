package com.disankyo.socket.bio.single;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 底层socket的服务端 单线程
 * @author xujianxin
 * @time Oct 14, 2013 10:25:10 AM
 */
public class EchoServer {
	
	private static final int PORT = 8000;
	private ServerSocket serverSocket = null;
	public EchoServer() throws IOException{
		serverSocket = new ServerSocket(PORT);
	}
	
	public void service(){
		Socket socket = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		
		while (true) {
			try {
				socket = serverSocket.accept();
				reader = getReader(socket);
				writer = getWriter(socket);
				
				String msg = null;
				while ((msg = reader.readLine()) != null) {
					writer.println(msg);
					writer.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(socket != null){
					try {
						socket.close();
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
		return new PrintWriter(new OutputStreamWriter(out));
	}
	
	public static void main(String[] args) throws IOException {
		new EchoServer().service();
	}
}
