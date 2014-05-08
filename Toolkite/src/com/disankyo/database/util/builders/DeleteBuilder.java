package com.disankyo.database.util.builders;

import com.disankyo.database.util.dialects.Dialect;

/**
 * 删除sql语句的封装
 * @author xjx
 *
 */
public class DeleteBuilder extends Builder{

	private WhereBuilder whereBuilder;//删除条件
	
	public DeleteBuilder(Dialect dialect, Class<?> entityClass){
		tableInfo = new TableInfo(entityClass, new String[0]);
		this.dialect = dialect;
		qualifierBuilder = new QualifierBuilder(dialect);
	}
	
	public WhereBuilder where(){
		whereBuilder = new WhereBuilder(tableInfo, this, qualifierBuilder);
		
		return whereBuilder;
	}
	
	public String toSql(){
		return dialect.delete(tableInfo, whereBuilder, qualifierBuilder);
	}
}
