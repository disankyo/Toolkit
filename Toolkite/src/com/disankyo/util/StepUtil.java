package com.disankyo.util;

/**
 * 进度完成状态的工具包 分别用0和1表示的二进制形式及字符串形式
 * @author xujianxin
 * @time Jun 4, 2013 4:32:19 PM
 */
public class StepUtil {

	/**
	 * 校验权限值是否含某一索引的权值(二进制)
	 * Jun 4, 2013 11:22:21 AM
	 * @param recordValue 记录权限的值
	 * @param index index 索引，从0开始
	 * @return
	 */
	public static boolean checkPower(int recordValue, int index){ 
		int currentValue = index << 1; 
		return (recordValue & currentValue) == currentValue; 
	} 
	
	/**
	 * 根据索引位设置新的权限值
	 * Jun 4, 2013 11:31:17 AM
	 * @param oldRecordValue 旧的记录权限的值
	 * @param index 索引，从0开始
	 * @return 返回新的值
	 */
	public static int getNewRecordValue(int oldRecordValue , int index){
		int newRecordValue = oldRecordValue + (index << 1);
		return newRecordValue;
	}
	
	/**
	 * 将记录步骤的字符串 oldSource 的第 index 个索引 ，替换成 flag,当index 大于等于 oldSource 的长度的时候，oldSource长度自动增长
	 * @param oldSource
	 * @param index 从0开始
	 * @param value 要改变的index索引的值
	 * @return
	 */
	public static String changeStep(
			String oldSource, int index, String value){
		
		if(index > 31){
			throw new RuntimeException("postion is too large!");
		}
		
		StringBuilder sb = new StringBuilder(oldSource);
		int len = sb.length();
		if(index >= len){
			for(int i = 0; i <= index - len; i++){
				sb.append("0");
			}
		}
		
		String newSource = sb.substring(0, index) + value + sb.substring(index + 1);
		
		return newSource;
	}
	
	/**
	 * 获取步骤中第index索引的值 
	 * Jul 12, 2013 1:44:48 PM
	 * @param source 
	 * @param index 从0开始
	 * @return
	 */
	public static String getStepValue(String source, int index){
		String value = null;
		if(index >= source.length()){
			value = "0";
		}else{
			value = source.substring(index, index + 1);
		}
		
		return value;
	}
	
	
	public static void main(String[] args) {
		
		
	}
}
