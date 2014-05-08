package com.disankyo.database.type;

import com.disankyo.database.NameParamPreparedStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据类型的接口，用于给指定类型的字段注入值或取出指定类型的字段值。
 * @param <T> 参数类型。
 * @version 1.00 2010-12-30 14:45:01
 * @since 1.6
 * @author disankyo
 */
public interface Type<T> {
    
    /**
     * 从ResultSet结果集中获取指定位置的值
     * @param result 结果集
     * @param index 指定位置
     * @return 值
     * @throws SQLException
     */
    public T getValue(ResultSet result, int index) throws SQLException;

    /**
     * 从ResultSet结果集中获取指定字段的值
     * @param result 结果集
     * @param fieldName 指定字段名称
     * @return 值
     * @throws SQLException
     */
    public T getValue(ResultSet result, String fieldName) throws SQLException;

    /**
     * 在PreparedStatement中设置指定位置的参数值
     * @param statement PreparedStatement
     * @param index 指定位置
     * @param value 参数值
     * @throws SQLException
     */
    public void setValue(PreparedStatement statement, int index, T value) throws SQLException;

    /**
     * 在PreparedStatement中设置指定字段名称的参数值
     * @param statement PreparedStatement
     * @param filedName 字段名称
     * @param value 参数值
     * @throws SQLException
     */
    public void setValue(NameParamPreparedStatement statement, String filedName, T value) throws SQLException;

    /**
     * 获取当前数据类型的Class对象
     * @return 当前相关的参数类型
     */
    @SuppressWarnings("unchecked")
    public Class getTypeClass();
}
