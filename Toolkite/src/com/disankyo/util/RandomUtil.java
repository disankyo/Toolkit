package com.disankyo.util;

import java.util.ArrayList;
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
		return random.nextInt(max);
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
		return random.nextInt(max);
	}
	
	
	/**
	 * 随机返回一个小于给定参数的int值(会等于上下限的值)
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
	 * 随机返回一个小于给定参数的int值(不会等于上限的值)
	 * @param min 给定参数的下限
	 * @param max 给定参数的上限
	 * @return
	 */
	public static int getRandomIntNotMax(int min, int max){
		if(min == max){
			return min;
		}
		
		if(min > max){
			int temp = min;
			min = max;
			max = temp;
		}
		
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}
	
	/**
	 * 使用一个带种子的随机生成器 返回一个小于给定参数的int值(会等于上下限的值)
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
	
	/**
	 * 随机获取列表中num个元素
	 * @param list
	 * @param num
	 * @return
	 */
	public static List<Object> getRandomList(List<Object> list, int num){
		List<Integer> indexs = new ArrayList<Integer>();
		for(int i = 0; i < list.size(); i++){
			indexs.add(i);
		}
		
		List<Object> result = new ArrayList<Object>();
		Object object = null;
		for(int i = 0; i < num; i++){
			int index = getRandomIntNotMax(i, list.size());
			object = list.get(index);
			result.add(object);
			if(index != i){
				list.set(index, list.get(i));
			}
		}
		
		return result;
	}
	
	/**
	 * 根据权重获取num个元素
	 * @param rateList 权重列表
	 * @param list 
	 * @param num
	 * @return
	 */
	public static <T> List<T> getRandomByRate(List<Integer> weightList, List<T> list, int num){
		int sumWeight = 0;//总的权重累加值
		
		int size = weightList.size();
		
		List<Integer> weightChance = new ArrayList<Integer>();
		int weight = 0;
		for(int i = 0; i < size; i++) {
			weight = weightList.get(i);
			if(i > 0){
				weightChance.add(weight + weightList.get(i - 1));
			} else {
				weightChance.add(weight);
			}
			
			sumWeight += weight;
		}
		
		List<T> result = new ArrayList<T>();
		int index = 0;
		int tempWeight = 0;
		for(int i = 0; i < num; i++){//获取num个结果
			int random = getRandomInt(1, sumWeight);
			for(int j = 0; j < size; j++){
				if(weightChance.get(j) >= random){
					tempWeight = weightList.get(j);
					
					sumWeight -= tempWeight;
					result.add(list.get(j));
					
					//元素被获取到 权重置为0
					weightChance.set(index, 0);
					index++;
					break;
				}
				
				for(; index < size; index++){//重新分配权重(获取权值的后面 权重去掉被选中的权重值) 
					if(weightChance.get(index) > 0){
						weightChance.set(index, weightChance.get(index) - tempWeight);
					}
				}
			}
		}
		
		return result;
	}
}
