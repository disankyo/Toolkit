package com.disankyo.database.util.builders;

import com.disankyo.database.util.dialects.Dialect;

/**
 * 查询sql语句的封装
 * @author xjx
 *
 */
public class SelectBuilder extends Builder {

	private String[] columns;//要查询的列
	private WhereBuilder whereBuilder;//查询条件
	
	public SelectBuilder(Dialect dialect){
		this.dialect = dialect;
		qualifierBuilder = new QualifierBuilder(dialect);
	}
	
	/**要查询的表名*/
	public SelectBuilder from(Class<?> entityClass){
		tableInfo = new TableInfo(entityClass, columns);
		return this;
	}
	
	/**拼接要查询的指定列*/
	public SelectBuilder columns(String ...columns){
		this.columns = columns;
		return this;
	}
	
	/**获取查询条件*/
	public WhereBuilder where(){
		whereBuilder = new WhereBuilder(tableInfo, this, qualifierBuilder);
		return whereBuilder;
	}
	
	public String toSql(){
		return dialect.select(tableInfo, whereBuilder, qualifierBuilder);
	}
	
}
