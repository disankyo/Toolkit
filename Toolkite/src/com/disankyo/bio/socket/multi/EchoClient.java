package com.disankyo.bio.socket.multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.logging.LogFactory;

import com.disankyo.log.SyncLogUtil;


/**
 * 底层socket的客户端
 * @author xujianxin
 * @time Oct 14, 2013 10:25:01 AM
 */
public class EchoClient {
	
	private static SyncLogUtil logger = new SyncLogUtil(LogFactory.getLog(EchoClient.class));
	
	private String host = "localhost";
	private int port = 8000;
	private Socket socket;

	public EchoClient() throws IOException {
		socket = new Socket(host, port);
	}

	public static void main(String args[]) throws IOException {
		
		new EchoClient().talk();
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

	public void talk() throws IOException {
		try {
			BufferedReader br = getReader(socket);
			PrintWriter pw = getWriter(socket);
			BufferedReader localReader = new BufferedReader(
					new InputStreamReader(System.in));
			String msg = null;
			while ((msg = localReader.readLine()) != null) {

				pw.println(msg);
				logger.debugLog("server back : " + br.readLine());

				if (msg.equals("bye"))
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
