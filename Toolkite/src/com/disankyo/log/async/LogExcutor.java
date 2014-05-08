package com.disankyo.log.async;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LogExcutor {

	private static Executor executor = Executors.newFixedThreadPool(3);
	
	public static void execute(LogTask logTask){
		executor.execute(logTask);
	}
}
