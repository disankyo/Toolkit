package com.disankyo.util;

import java.util.List;
import java.util.Random;

/**
 * 随机的工具类
 * @author xujianxin
 * @time Jun 4, 2013 4:35:12 PM
 */
public class RandomUtil {

	/**
	 * 随机返回一个小于给定参数的int值
	 * @param max 给定参数的上限
	 * @return
	 */
	public static int getRandomInt(int max){
		if(max <= 0){
			return 0;
		}
		
		Random random = new Random();
		return random.nextInt();
	}
	
	/**
	 * 使用一个带种子的随机生成器 返回一个小于给定参数的int值
	 * @param max 给定参数的上限
	 * @param seed 种子
	 * @return
	 */
	public static int getRandomInt(int max, long seed){
		if(max <= 0){
			return 0;
		}
		
		Random random = new Random();
		random.setSeed(seed);
		return random.nextInt();
	}
	
	
	/**
	 * 随机返回一个小于给定参数的int值
	 * @param min 给定参数的下限
	 * @param max 给定参数的上限
	 * @return
	 */
	public static int getRandomInt(int min, int max){
		if(min == max){
			return min;
		}
		
		if(min > max){
			int temp = min;
			min = max;
			max = temp;
		}
		
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}
	
	/**
	 * 使用一个带种子的随机生成器 返回一个小于给定参数的int值
	 * @param max 给定参数的上限
	 * @param seed 种子
	 * @return
	 */
	public static int getRandomInt(int min, int max, long seed){
		if(min == max){
			return 0;
		}
		
		Random random = new Random();
		random.setSeed(seed);
		return random.nextInt(max - min + 1) + min;
	}
	
	/**
	 * 根据权重值的列表 随机获取权重的索引
	 * @param weightList 权重值的列表
	 * @return 权重的索引
	 */
	public static int getWeightIndex(List<Integer> weightList){
		int allWeight = 0;
		for(int i = 0; i < weightList.size(); i++){
			allWeight += weightList.get(i);
		}
		
		int randomValue = RandomUtil.getRandomInt(1, allWeight);
		
		int tempValue = 0;
		for(int index = 0; index < weightList.size(); index++){
			tempValue += weightList.get(index);
			if(randomValue >= tempValue){
				return index;
			}
		}
		
		return 0;
	}
	
	/**
	 * 根据权重值的列表 随机获取权重的索引
	 * @param weightList 权重值的列表
	 * @param seed 种子
	 * @return 权重的索引
	 */
	public static int getWeightIndex(List<Integer> weightList, long seed){
		int allWeight = 0;
		for(int i = 0; i < weightList.size(); i++){
			allWeight += weightList.get(i);
		}
		
		int randomValue = RandomUtil.getRandomInt(1, allWeight, seed);
		
		int tempValue = 0;
		for(int index = 0; index < weightList.size(); index++){
			tempValue += weightList.get(index);
			if(randomValue >= tempValue){
				return index;
			}
		}
		
		return 0;
	}
	
	
}
