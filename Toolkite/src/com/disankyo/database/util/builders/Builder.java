package com.disankyo.database.util.builders;

import com.disankyo.database.util.dialects.Dialect;

public abstract class Builder {

	Dialect dialect;
	QualifierBuilder qualifierBuilder;//查询语句的条件修饰
	TableInfo tableInfo;//目标表信息
	
	public abstract String toSql();
	
	/**升序排列*/
	public Builder asc(String column){
		qualifierBuilder.orderBy(column, QualifierBuilder.Order.ASC);
		return this;
	}
	
	/**降序排列*/
	public Builder desc(String column){
		qualifierBuilder.orderBy(column, QualifierBuilder.Order.DESC);
		return this;
	}
	
	public Builder groupBy(String column){
		qualifierBuilder.groupby(column);
		return this;
	}
	
}
