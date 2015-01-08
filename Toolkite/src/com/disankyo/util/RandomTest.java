package com.disankyo.util;

import java.util.ArrayList;
import java.util.List;

public class RandomTest {

	public static void main(String[] args) {
		
		long t1 = System.currentTimeMillis();
		RateValidator<Integer> list = new RateValidator<Integer>();
		for(int i = 0; i < 100000; i++){
			list.put(i, 0);
		}
		
		for(int i = 0; i < 10000; i++){
			list.getResultAndRemove();
		}
		long t2 = System.currentTimeMillis();
		System.out.println("============"+(t2-t1));
		
		long t3 = System.currentTimeMillis();
		List<Object> list2 = new ArrayList<Object>();
		for(int i = 0;i < 100000; i++){
			list2.add(i);
		}
		
		RandomUtil.getRandomList(list2, 10000);
		
		long t4 = System.currentTimeMillis();
		System.out.println("============"+(t4-t3));
		
		
		for(int i = 0; i < 10; i++){
			System.out.println(RandomUtil.getRandomIntNotMax(0, 10));
//			System.out.println(RandomUtil.getRandomInt(10));
		}
		for(int j = 0; j < 10; j++){
			System.out.println("-----"+RandomUtil.getRandomInt(10));
		}
	}
}
