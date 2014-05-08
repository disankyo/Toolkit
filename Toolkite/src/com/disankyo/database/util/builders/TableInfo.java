package com.disankyo.database.util.builders;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;

import com.disankyo.database.util.ReflectUtil;


public class TableInfo {

	private Class<?> entityClass;
	private String name;
	private List<String> columns = new ArrayList<String>();

	public TableInfo(Class<?> entityClass, String... columns) {
		this.entityClass = entityClass;
		this.name = entityClass.getSimpleName().toLowerCase();
		if(columns != null){
			this.columns.addAll(Arrays.asList(columns));
		}
	}

	/** 把要查询的列拼接处理 */
	public StringBuilder splicingSelectableColumns() {
		StringBuilder result = new StringBuilder();
		if (columns.isEmpty()) {
			result.append(name).append(".*");
		} else {
			for (String column : columns) {
				result.append(name).append(".").append(column).append(",");
			}
			
			result.deleteCharAt(result.length() - 1);
		}
		
		return result;
	}

	/** 列前加表名做前缀 */
	public StringBuilder toColumnPrefix(String column) {
		return new StringBuilder(name).append(".").append(column);
	}
	
	/**获取类中默认的可插入数据库的字段*/
	public List<String> getInsertableColumns() {
		
		List<Field> tableColumns = ReflectUtil.getPropertyDescriptorsFromFields(entityClass);
		List<String> insertColumns = new ArrayList<String>();

		for (Field field : tableColumns) {
			if (ReflectUtil.isTransient(field)
					|| ReflectUtil.isStatic(field)
					|| !field.getDeclaringClass().equals(entityClass)
					|| ReflectUtil.isGeneratedValue(field)
					|| ReflectUtil.isIdAccessor(field)
					|| (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).insertable())) {
				continue;
			}
			
			insertColumns.add(field.getName());
		}

		return insertColumns;
	}
	
	/**获取类中默认的可插入数据库的字段*/
	public List<String> getUpdateableColumns() {
		
		List<Field> tableColumns = ReflectUtil.getPropertyDescriptorsFromFields(entityClass);
		List<String> updateColumns = new ArrayList<String>();

		for (Field field : tableColumns) {
			if (ReflectUtil.isTransient(field)
					|| ReflectUtil.isStatic(field)
					|| !field.getDeclaringClass().equals(entityClass)
					|| ReflectUtil.isGeneratedValue(field)
					|| ReflectUtil.isIdAccessor(field)
					|| (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).updatable())) {
				continue;
			}

			updateColumns.add(field.getName());
		}

		return updateColumns;
	}

	/** 把要查询的表拼接处理 */
	public StringBuilder toTables() {
		return new StringBuilder(name);
	}
}
