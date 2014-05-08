package com.disankyo.database.util.builders;

import com.disankyo.database.util.dialects.Dialect;

/**插入sql语句的封装*/
public class InsertBuilder extends Builder{

	private String[] columns;//要插入的列
	
	public InsertBuilder(Class<?> entityClass, Dialect dialect){
		tableInfo = new TableInfo(entityClass, columns);
		this.dialect = dialect;
	}
	
	public InsertBuilder columns(String ...columns){
		this.columns = columns;
		return this;
	}
	
	public String toSql(){
		return dialect.insert(tableInfo, columns);
	}
}
