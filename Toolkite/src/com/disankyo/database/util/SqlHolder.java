package com.disankyo.database.util;

import java.lang.reflect.Field;

/**
 * 通用查询方法调用入口
 * @author xjx
 *
 */
public class SqlHolder {

	/**插入数据方法*/
	public static String insert(Class<?> entityClass){
		return SqlQuery.insert(entityClass).toSql();
	}
	
	/**查询实体类的通用方法*/
	public static String select(Class<?> entityClass) {
		return SqlQuery.select().from(entityClass).toSql();
	}
	
	/**根据主键id查询实体类的通用方法*/
	public static String selectById(Class<?> entityClass) {
		Field field = ReflectUtil.getIdAccessor(entityClass);
		return SqlQuery.select().from(entityClass).where().eq(field.getName()).toSql();
	}
	
	/**根据主键id更新实体类*/
	public static String updateById(Class<?> entityClass){
		Field field = ReflectUtil.getIdAccessor(entityClass);
		return SqlQuery.update(entityClass).where().eq(field.getName()).toSql();
	}
	
	/**根据主键id删除记录*/
	public static String deleteById(Class<?> entityClass){
		Field field = ReflectUtil.getIdAccessor(entityClass);
		return SqlQuery.delete(entityClass).where().eq(field.getName()).toSql();
	}
}
