package com.disankyo.database.util.builders;


/**
 * sql条件语句的封装
 * @author xjx
 *
 */
public class WhereBuilder extends Builder{
	
	private Builder builder;//要执行的操作（selectBuilder,updateBuilder,insertBuilder,deleteBuilder）
	private final StringBuilder sqlBuilder = new StringBuilder();
	
	public static enum Item{
		AND, OR
	}

	public WhereBuilder(TableInfo tableInfo, Builder builder, QualifierBuilder qualifierBuilder){
		super.tableInfo = tableInfo;
		this.builder = builder;
		super.qualifierBuilder = qualifierBuilder;
	}
	
	public WhereBuilder eq(String column){
		eq(Item.AND, column);
		return this;
	}
	
	public WhereBuilder eq(Item item, String column){
		write(item, column, "=");
		return this;
	}
	
	public WhereBuilder allEq(String ...columns){
		for (String column : columns) {
			eq(Item.AND, column);
		}
		return this;
	}
	
	/**大于 > */
	public WhereBuilder gt(String column){
		gt(Item.AND, column);
		return this;
	}
	/**大于 > */
	public WhereBuilder gt(Item item, String column){
		write(item, column, ">");
		return this;
	}
	
	/**小于 < */
	public WhereBuilder lt(String column){
		lt(Item.AND, column);
		return this;
	}
	/**小于 < */
	public WhereBuilder lt(Item item, String column){
		write(item, column, "<");
		return this;
	}
	
	private void write(Object prefix, String column, String sign){
		sqlBuilder.append(' ');
		if(sqlBuilder.length() > 1){
			sqlBuilder.append(prefix).append(' ');
		}
		sqlBuilder.append(tableInfo.toColumnPrefix(column)).append(' ').append(sign).append(" ?");
	}
	
	/**查询条件组装成sql*/
	public String toSql(StringBuilder sql){
		return sql.append(sqlBuilder).toString();
	}
	
	public String toSql(){
		if(builder instanceof SelectBuilder){
			SelectBuilder selectBuilder = (SelectBuilder) builder;
			return selectBuilder.toSql();
		} else if(builder instanceof UpdateBuilder){
			UpdateBuilder updateBuilder = (UpdateBuilder) builder;
			return updateBuilder.toSql();
		} else if(builder instanceof DeleteBuilder){
			DeleteBuilder deleteBuilder = (DeleteBuilder) builder;
			return deleteBuilder.toSql();
		}
		
		return null;
	}
}
