package com.disankyo.database.util.builders;

import com.disankyo.database.util.dialects.Dialect;

/**更新sql语句的封装*/
public class UpdateBuilder extends Builder{

	private WhereBuilder whereBuilder;//更新的条件
	private String[] columns;//显式要更新的列
	
	public UpdateBuilder(Dialect dialect, Class<?> entityClass){
		tableInfo = new TableInfo(entityClass, columns);
		this.dialect = dialect;
		qualifierBuilder = new QualifierBuilder(dialect);
	}
	
	/**拼接要更新的指定列*/
	public UpdateBuilder set(String ...columns){
		this.columns = columns;
		return this;
	}
	
	public WhereBuilder where(){
		whereBuilder = new WhereBuilder(tableInfo, this, qualifierBuilder);
		return whereBuilder;
	}
	
	public String toSql(){
		return dialect.update(tableInfo, whereBuilder, qualifierBuilder, columns);
	}
}
