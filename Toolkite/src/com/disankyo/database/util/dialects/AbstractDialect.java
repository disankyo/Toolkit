package com.disankyo.database.util.dialects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.disankyo.database.util.builders.QualifierBuilder;
import com.disankyo.database.util.builders.TableInfo;
import com.disankyo.database.util.builders.WhereBuilder;

/**
 * 通用的sql相关封装语句
 * 
 * @author xjx
 * 
 */
public class AbstractDialect implements Dialect {
	
	/**
	 * 查询语句的实现
	 * @param tableInfo 表信息
	 * @param whereBuilder 查询条件
	 * @param qualifierBuilder 查询条件修饰
	 * @return
	 */
	public String select(TableInfo tableInfo, WhereBuilder whereBuilder, QualifierBuilder qualifierBuilder){
		
		StringBuilder selectSql = new StringBuilder("SELECT ");
		//拼接列
		selectSql.append(tableInfo.splicingSelectableColumns());
		
		selectSql.append(" FROM ");
		//拼接表
		selectSql.append(tableInfo.toTables());
		
		if(whereBuilder != null){
			selectSql.append(" WHERE");
			whereBuilder.toSql(selectSql);
		}
		
		return selectSql.append(qualifierBuilder.toSql()).toString();
	}
	
	/**
	 * 插入语句的基本实现
	 * @param tableInfo 表信息
	 * @param columns 要插入的列信息（没有的话 是插入所有可插入的列）
	 * @return
	 */
	public String insert(TableInfo tableInfo, String ...columns){
		StringBuilder insertSql = new StringBuilder("INSERT INTO ");
		insertSql.append(tableInfo.toTables()).append('(');
		
		//获取要插入列的列表
		List<String> columnList = new ArrayList<String>();
		if(columns == null){
			columnList = tableInfo.getInsertableColumns();
		} else {
			columnList.addAll(Arrays.asList(columns));
		}
		//拼接要插入的列
		for (String column : columnList) {
			insertSql.append(column).append(",");
		}
		insertSql.deleteCharAt(insertSql.length() - 1).append("） VALUES(");
		
		for (@SuppressWarnings("unused")String column : columnList) {
			insertSql.append("?,");
		}
		insertSql.deleteCharAt(insertSql.length() - 1).append(")");
		
		return insertSql.toString();
	}
	
	/**
	 * 更新语句的实现
	 * @param tableInfo 表信息
	 * @param whereBuilder 更新条件
	 * @param columns 要更新的列（没有的话 是更新所有可更新的列）
	 * @return
	 */
	public String update(TableInfo tableInfo, WhereBuilder whereBuilder,
			QualifierBuilder qualifierBuilder, String ...columns){
		
		StringBuilder updateSql = new StringBuilder("UPDATE ");
		updateSql.append(tableInfo.toTables()).append(" SET ");
		//获取要更新列的列表
		List<String> columnList = new ArrayList<String>();
		if(columns == null){
			columnList = tableInfo.getUpdateableColumns();
		} else {
			columnList.addAll(Arrays.asList(columns));
		}
		//拼接要更新的列
		for (String column : columnList) {
			updateSql.append(column).append(" = ?,");
		}
		updateSql.deleteCharAt(updateSql.length() - 1);
		
		if(whereBuilder != null){
			updateSql.append(" WHERE");
			whereBuilder.toSql(updateSql);
		}
		
		return updateSql.append(qualifierBuilder.toSql()).toString();
	}

	/**
	 * 删除语句的实现
	 * @param tableInfo 表信息
	 * @param whereBuilder 删除条件
	 * @return
	 */
	public String delete(TableInfo tableInfo,
			WhereBuilder whereBuilder, QualifierBuilder qualifierBuilder){
		StringBuilder deleteSql = new StringBuilder("DELETE FROM ");
		deleteSql.append(tableInfo.toTables());
		if(whereBuilder != null){
			deleteSql.append(" WHERE ");
			whereBuilder.toSql(deleteSql);
		}
		
		return deleteSql.append(qualifierBuilder.toSql()).toString();
	}

	/**
	 * 数据库操作的条件修饰
	 * @param order 排序条件
	 * @param limit 限制数量
	 * @return
	 */
	public String qualify(StringBuilder order, int limit) {
		if(limit > -1){
			order.append(" LIMIT ").append(limit);
		}
		return order.toString();
	}
}
