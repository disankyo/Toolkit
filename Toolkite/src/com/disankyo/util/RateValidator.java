package com.disankyo.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.LogFactory;
import com.disankyo.log.SyncLogUtil;


/**
 * 权重选择器
 * @author xujianxin
 * @time Oct 24, 2013 3:03:31 PM
 */
public class RateValidator<T> {
	
	private static final SyncLogUtil LOG = new SyncLogUtil(LogFactory.getLog(RateValidator.class));

	public List<T> objectList = new ArrayList<T>();
	public List<Integer> weightList = new ArrayList<Integer>();
	
	public RateValidator(){
	}
	
	/**
	 * 把对象及对应的权重 放到选择器中
	 * @param object 对象
	 * @param weight 权重值
	 */
	public void put(T object, Integer weight){
		objectList.add(object);
		weightList.add(weight);
	}
	
	/**
	 * 根据权重值列表 随机获取权重值的对象
	 * @return
	 */
	public T getResult(){
		int index = RandomUtil.getWeightIndex(weightList);
		
		if(index < 0 || index >= weightList.size()){
			LOG.errorLog(new NullPointerException(), "");
			return null;
		}
		
		return objectList.get(index);
	}
	
	
	/**
	 * 根据权重值列表 随机获取权重值的对象
	 * @return
	 */
	public T getResult(long seed){
		int index = RandomUtil.getWeightIndex(weightList, seed);
		
		if(index < 0 || index >= weightList.size()){
			LOG.errorLog(new IllegalArgumentException(), "参数异常");
			return null;
		}
		
		return objectList.get(index);
	}
	
	/**
	 * 根据权重值列表 随机获取权重值的对象
	 * 并删除权重选择器中的该对象
	 * @return
	 */
	public T getResultAndRemove(){
		
		T object = getResult();
		remove(object);
		return object;
	}
	
	/**
	 * 根据权重值列表 随机获取权重值的对象
	 * 并删除权重选择器中的该对象
	 * @return
	 */
	public T getResultAndRemove(long seed){
		
		T object = getResult(seed);
		remove(object);
		return object;
	}
	
	/**
	 * 清除权重选择器中的所有对象
	 */
	public void clear(){
		objectList.clear();
		weightList.clear();
	}
	
	/**
	 * 删除权重选择器中的该对象
	 * @param object
	 */
	public void remove(T object){
		
		int index = objectList.indexOf(object);
		
		objectList.remove(index);
		weightList.remove(index);
	}
	
}
