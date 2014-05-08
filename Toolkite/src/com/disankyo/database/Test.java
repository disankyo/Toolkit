package com.disankyo.database;

import java.lang.reflect.Field;

import com.disankyo.database.util.ReflectUtil;
import com.disankyo.database.util.SqlHolder;
import com.disankyo.database.util.SqlQuery;

public class Test {
	
	public static void main(String[] args) {
		Class<Mail> entityClass = Mail.class;
		
		Field field = ReflectUtil.getIdAccessor(entityClass);
		System.out.println(SqlQuery.select().from(entityClass).where().allEq(field.getName(), "colString").desc("333").toSql());
		
		System.out.println(SqlQuery.select().columns("wwww").from(entityClass).where().allEq(field.getName(), "colString").groupBy("eee").toSql());
		
		System.out.println(SqlQuery.insert(Mail.class).columns("status", "type").toSql());
		
		System.out.println(SqlQuery.update(entityClass).where().allEq(field.getName()).desc("www").toSql());
		
		System.out.println(SqlQuery.delete(entityClass).asc("1111").groupBy("222").toSql());
		
		System.out.println(SqlHolder.insert(UserBuffer.class));
		
	}
}
