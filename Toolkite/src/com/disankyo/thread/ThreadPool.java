package com.disankyo.thread;

import java.util.LinkedList;



/**
 * 线程池实现
 * @author xujianxin
 * @time Oct 15, 2013 4:55:31 PM
 */
public class ThreadPool extends ThreadGroup{
	
	private LinkedList<Runnable> workQueue;//执行任务的线程队列
	private boolean isClose = false;//队列是否关闭
	private static int threadPoolId;//线程池的id
	private static int threadId;//池中任务线程的id
	
	public ThreadPool(int poolSize){
		super("threadPoolId:" + (threadPoolId++));
		setDaemon(true);
		workQueue = new LinkedList<Runnable>();
		for (int i = 0; i < poolSize; i++) {
			new WorkThread().start();
		}
	}
	
	/**
	 * 添加新的任务到队列中
	 * @param task 任务线程
	 */
	public void execute(Runnable task){
		if(isClose){
			throw new IllegalStateException();
		}
		
		if(task != null){
			workQueue.add(task);
			notify();
		}
	}
	
	/**
	 * 从队列中获取一个任务去执行
	 * @return
	 * @throws InterruptedException
	 */
	Runnable getTask() throws InterruptedException{
		if(workQueue.size() == 0){
			if(isClose){
				return null;
			}
			wait();
		}
		return workQueue.removeFirst();
	}
	
	/**终止任务的执行 并清空队列中的任务*/
	public void colse(){
		if(!isClose){
			isClose = true;
			workQueue.clear();
			interrupt();
		}
	}
	
	public void join(){
		
	}
	
	
	class WorkThread extends Thread{
		
		public WorkThread(){
			super(ThreadPool.this, "WorkThread-" + threadId++);
		}
		
		@Override
		public void run() {
			
		}
	}
}


