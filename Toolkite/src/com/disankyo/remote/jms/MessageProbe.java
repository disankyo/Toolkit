package com.disankyo.remote.jms;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * 一个消息探测器，提前注册实例化该类，当调用obtainReturnValue之后
 * await方法获取返回值，在此之前处于阻塞状态
 * @version 1.00 2012-3-29 12:21:45
 * @since 1.6
 * @author allean
 */
public class MessageProbe {

    private Map<String, Serializable> returnValue;
    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * 获取远程方法调用的返回值，没有无期限等待的话，就返回远程方法的返回值
     * @return 远程方法的返回值
     */
    public Map<String, Serializable> await()
            throws InterruptedException {
        latch.await();
        return returnValue;
    }

    /**
     * 不会无期限等待，当超过指定的超时时间时，会抛出异常。
     * @param timeout 设定的超时时间
     * @return 远程方法的返回值
     */
    public Map<String, Serializable> await(long timeout)
            throws InterruptedException, TimeoutException {
        boolean isTimeout = false;
        isTimeout = latch.await(timeout, TimeUnit.MILLISECONDS);
        if (isTimeout) {
            return returnValue;
        } else {
            throw new TimeoutException("Repose message time out.");
        }
    }

    /**
     * 设定远程方法返回值,同时在await方法上的阻塞全部取消
     * @param value 远程方法返回值
     */
    public void obtainReturnValue(Map<String, Serializable> value) {
        returnValue = value;
        latch.countDown();
    }
}
