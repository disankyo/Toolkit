package com.disankyo.database.util.dialects;

import com.disankyo.database.util.builders.QualifierBuilder;
import com.disankyo.database.util.builders.TableInfo;
import com.disankyo.database.util.builders.WhereBuilder;

public interface Dialect {

	/**
	 * 查询语句的实现
	 * @param tableInfo 表信息
	 * @param whereBuilder 查询条件
	 * @param qualifierBuilder 查询条件修饰
	 * @return
	 */
	String select(TableInfo tableInfo, WhereBuilder whereBuilder, QualifierBuilder qualifierBuilder);
	
	/**
	 * 插入语句的基本实现
	 * @param tableInfo 表信息
	 * @param columns 要插入的列信息（没有的话 是插入所有可插入的列）
	 * @return
	 */
	String insert(TableInfo tableInfo, String ...columns);
	
	/**
	 * 更新语句的实现
	 * @param tableInfo 表信息
	 * @param whereBuilder 更新条件
	 * @param qualifierBuilder 更新条件修饰
	 * @param columns 要更新的列（没有的话 是更新所有可更新的列）
	 * @return
	 */
	public String update(TableInfo tableInfo, WhereBuilder whereBuilder, QualifierBuilder qualifierBuilder, String ...columns);
	
	/**
	 * 删除语句的实现
	 * @param tableInfo 表信息
	 * @param whereBuilder 删除条件
	 * @param qualifierBuilder 删除条件修饰
	 * @return
	 */
	public String delete(TableInfo tableInfo, WhereBuilder whereBuilder, QualifierBuilder qualifierBuilder );
	
	/**
	 * 数据库操作的条件修饰
	 * @param order 排序条件
	 * @param limit 限制数量
	 * @return
	 */
	public String qualify(StringBuilder order, int limit);
}
