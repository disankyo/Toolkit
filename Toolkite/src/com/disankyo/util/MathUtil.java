package com.disankyo.util;

import java.math.BigDecimal;

/**
 * 数据相关的基本操作
 * @author xjx
 *
 */
public class MathUtil{

	/**转int直接取整 去掉小数部分*/
	public static int getIntValue(double value){
		return (int)value;
	}
	
	/**
	 * 转int 四舍五入
	 * Math.round性能不好,而且是查找最近的整数
	 */
	public static int getRoundValue(double value){
		return (int)(value + 0.5f);
	}
	
	/**
	 * float转double的精确转换
	 * 但是性能不好 对精确度要求很高时才使用（例如金钱计算时）
	 * @param value
	 * @return
	 */
	public static double floatToDouble(float value){
		BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
	    double result = bigDecimal.doubleValue();
	    return result;
	}
	
	
	public static void main(String[] args) {
		   float f = 1024.12f;
		   System.out.println(floatToDouble(f));
	}
	
	
}
