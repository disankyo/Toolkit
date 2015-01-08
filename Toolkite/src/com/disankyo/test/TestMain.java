package com.disankyo.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.disankyo.log.ErrorLog;
import com.disankyo.log.LogUtil;
import com.disankyo.log.LoggerFactory;
import com.disankyo.util.ByteUtil;

/**
 *
 * @author xujianxin
 * @time Oct 22, 2013 3:17:23 PM
 */
public class TestMain {
	
	
	
	private static LogUtil logUtil = LoggerFactory.getAsynchLog(TestMain.class);

	private List<Integer> list = new ArrayList<Integer>();
	
	private static int id;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		System.out.println(1 << 30);
//		System.out.println(1024 << 7);
//		System.out.println(Runtime.getRuntime().availableProcessors());
//		
//		Executor executor = Executors.newCachedThreadPool();
//		TestMain main = new TestMain();
//		for (int i = 0; i < 1000; i++) {
//			executor.execute(main.new MyRunnable(i));
//		}
//		
//		for (int i = 0; i < 1000; i++) {
//			executor.execute(main.new MyRunnable1());
//		}
		
		
//		int x = 25;
//		byte[] bytes = ByteUtil.intToByte(x);
		
		String ss = "二进制";
		byte[] bytes = ByteUtil.objectToByte(ss);
		for (byte b : bytes) {
			System.out.println(ByteUtil.bitString(b));
		}
		
		
	}
	
	  
	class MyRunnable implements Runnable{

		private int value;
		
		public MyRunnable(int value){
			this.value = value;
		}
		
		@Override
		public void run() {
			list.add(value);
		}
	}
	
	
	class MyRunnable1 implements Runnable{
//		private int value;
//		
//		public MyRunnable1(int value){
//			this.value = value;
//		}
		@Override
		public void run() {
			Iterator<Integer> iterator = list.iterator();
			
			try {
				while (iterator.hasNext()) {
					Integer value = (Integer) iterator.next();
					logUtil.infoLog(id++ +" "+value);
					iterator.remove();
				}
			} catch (Exception e) {
				logUtil.infoLog("===="+e.getMessage());
				ErrorLog.error(e, e.getMessage());
			}
			
//			for(Integer value : list){
//				System.out.println(id++ +"============="+value);
//			}
		}
	}
}
