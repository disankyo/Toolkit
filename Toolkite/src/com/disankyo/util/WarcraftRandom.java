package com.disankyo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author xjx
 * 伪随机分布算法
 * 是指魔兽争霸III引擎中为某些百分率攻击修正的动力可能性计算。这个概率不是取一个静态百分数，而是开始 设为一个很小的初始值，
 * 然后如果连续的攻击修正没有发生，他会渐渐地变大。但攻击修正起作用时，概率会降回 为初始值。
 * 这样系统不可能实现一连串的成功修正，也会让整个游戏至少有一个攻击修正，最终概率会超过100 %并“强制”在下一个攻击中实现修正。
 * 所以攻击修正的分布不是真正的随即，也即称之为 伪随机分布。
 */
public class WarcraftRandom {
	/**
	 * key : actual rate value : 理论概率常数
	 * */
	private static Map<Double, Double> rateMap;
	static {
		rateMap = new HashMap<Double, Double>();
		rateMap.put(0.05, 0.00380);
		rateMap.put(0.10, 0.01475);
		rateMap.put(0.15, 0.03222);
		rateMap.put(0.20, 0.05570);
		rateMap.put(0.25, 0.08474);

		rateMap.put(0.30, 0.11895);
		rateMap.put(0.35, 0.15798);
		rateMap.put(0.40, 0.20155);
		rateMap.put(0.45, 0.24931);
		rateMap.put(0.50, 0.30210);

		rateMap.put(0.55, 0.36040);
		rateMap.put(0.60, 0.42265);
		rateMap.put(0.65, 0.48113);
		rateMap.put(0.70, 0.57143);
		rateMap.put(0.75, 0.66667);

		rateMap.put(0.80, 0.75000);
		rateMap.put(0.85, 0.82353);
		rateMap.put(0.90, 0.88889);
		rateMap.put(0.95, 0.94737);
	}

	/**
	 * @param time
	 *            在这次投色子之前有多少次没有命中
	 * @param actualRate
	 *            真实概率
	 * @return 本次随机的命中概率值
	 */
	public static double getRate(int time, double actualRate) {
		Double basicRate = rateMap.get(actualRate);
		return basicRate * (time + 1);
	}

	/**
	 * 用于测试伪随机分布算法
	 * @param args
	 */
	public static void main(String[] args) {
		int not_crit_count = 0;
		int totalTry= 100000000;
		double rate = 0.3;
		int totalCrit = 0;
		for (int i = 0; i < totalTry; i++) {
			if (new Random().nextDouble() > getRate(not_crit_count, rate)){
				not_crit_count ++;
			} else {
				not_crit_count = 0;
				totalCrit ++;
			}
		}
		System.out.println("总尝试次数：" + totalTry);
		System.out.println("暴击率期望值：" + rate);
		System.out.println("暴击率实际值：" + (double)totalCrit/(double)totalTry);
	}
}
