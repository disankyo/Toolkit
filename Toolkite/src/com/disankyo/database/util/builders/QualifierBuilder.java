package com.disankyo.database.util.builders;

import com.disankyo.database.util.dialects.Dialect;

/**
 * 修饰条件的封装
 * @author xjx
 *
 */
public class QualifierBuilder extends Builder{
	
	public static enum Order{
		ASC,DESC
	}

	private StringBuilder qualifierSql = new StringBuilder();
	private int limit = -1;//limit现在数量
	
	public QualifierBuilder(Dialect dialect){
		this.dialect = dialect;
	}
	
	/**查询数量限制*/
	public QualifierBuilder limit(int limit){
		this.limit = limit;
		return this;
	}
	
	public void groupby(String column){
		qualifierSql.append(" GROUP BY ").append(column);
	}
	
	/**排序sql语句封装*/
	public void orderBy(String column, QualifierBuilder.Order order){
		if(qualifierSql.length() == 0){
			qualifierSql.append(" ORDER BY ");
		} else {
			qualifierSql.append(",");
		}
		
		qualifierSql.append(column).append(" ").append(order);
	}
	
	public String toSql(){
		return dialect.qualify(qualifierSql, limit);
	}
}
