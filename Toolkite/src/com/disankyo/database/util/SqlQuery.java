package com.disankyo.database.util;

import com.disankyo.database.util.builders.DeleteBuilder;
import com.disankyo.database.util.builders.InsertBuilder;
import com.disankyo.database.util.builders.SelectBuilder;
import com.disankyo.database.util.builders.UpdateBuilder;
import com.disankyo.database.util.dialects.AbstractDialect;
import com.disankyo.database.util.dialects.Dialect;


public class SqlQuery {
	
	private static final Dialect dialect = new AbstractDialect();
	
	public static SelectBuilder select(){
		return new SelectBuilder(dialect);
	}
	
	public static InsertBuilder insert(Class<?> entityClass){
		return new InsertBuilder(entityClass, dialect);
	}
	
	public static UpdateBuilder update(Class<?> entityClass){
		return new UpdateBuilder(dialect, entityClass);
	}
	
	public static DeleteBuilder delete(Class<?> entityClass){
		return new DeleteBuilder(dialect, entityClass);
	}
	
}
