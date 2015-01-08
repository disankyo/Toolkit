package com.disankyo.id;

import java.util.HashMap;
import java.util.Map;

/**
 * 来自于twitter项目的id生成方案 全局唯一且 时间有序
 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 * 
 * @author xjx
 * 
 */
public class IdWorker {

	public static IdWorker idWorker;

	private final static long twepoch = 1288834974657L;
	// 机器标识位数
	private final static long workerIdBits = 5L;
	// 数据中心标识位数
	private final static long dataCenterIdBits = 5L;
	// 机器ID最大值
	private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// 数据中心ID最大值
	private final static long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
	// 毫秒内自增位
	private final static long sequenceBits = 12L;
	// 机器ID偏左移12位
	private final static long workerIdShift = sequenceBits;
	// 数据中心ID左移17位
	private final static long dataCenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒左移22位
	private final static long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

	private static long lastTimestamp = -1L;

	private long sequence = 0L;

	// 唯一标识生成的业务机器id(默认配1,数据量很大时可以考虑根据平台来配置)
	private final long workerId;
	// 数据中心id(默认配1,当量级很大需要数据中心时可以考虑通过配置设置)
	private final long dataCenterId;

	public static IdWorker getInstance() {
		if (idWorker == null) {
			idWorker = new IdWorker(1, 1);
		}

		return idWorker;
	}

	public IdWorker(long workerId, long dataCenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
		}
		if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
			throw new IllegalArgumentException(
					"datacenter Id can't be greater than %d or less than 0");
		}
		this.workerId = workerId;
		this.dataCenterId = dataCenterId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			try {
				throw new Exception("Clock moved backwards.  Refusing to generate id for "
						+ (lastTimestamp - timestamp) + " milliseconds");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (lastTimestamp == timestamp) {
			// 当前毫秒内，则+1
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				// 当前毫秒内计数满了，则等待下一秒
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		// ID偏移组合生成最终的ID，并返回ID
		long nextId = ((timestamp - twepoch) << timestampLeftShift)
				| (dataCenterId << dataCenterIdShift) | (workerId << workerIdShift) | sequence;

		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		final int size = 1000000;
		Map<Integer, Long> map = new HashMap<Integer, Long>(size);
		long result = 0;
		int num = 0;
		IdWorker idWorker = IdWorker.getInstance();
		for (int i = 0; i < size; i++) {
			result = idWorker.nextId();
			while (map.containsValue(result)) {
				result = idWorker.nextId();
				num++;
			}

			map.put(i, result);
		}
		long t2 = System.currentTimeMillis();
		System.out.println("=use time=" + (t2 - t1));
		System.out.println("==num===" + num);
	}
}
