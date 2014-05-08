package com.disankyo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import com.disankyo.database.type.Type;

/**
 * 数据库操作帮助类
 * @version 1.00 2010-12-30 14:32:53
 * @since 1.6
 * @author disankyo
 */
@SuppressWarnings("unchecked")
public class DataSourceUtil {
	
    private static final String PARAMETER_ERROR_MESSAGE =
            "Parameter type and parameter values of the container must be consistent.";

    /**
     * 批量执行更新或者创建SQL语句。
     * 如果每批次的数量设置小于1，那么将默认为1.
     * Connection为null或paramStyles和paramValues不同时为null将抛出IllegalArgumentException异
     * 常。
     * 如果参数类型列表和值列表中的某个元素数组长度不一致也将抛出
     * IllegalArgumentException。
     *
     * @param sql 需要执行的SQL语句
     * @param paramTypes 需要插入的SQL参数类型的列表
     * @param paramValues 需要插入的SQL参数值的列表
     * @param connection Connection
     * @param batchSize 批量提交的每批次最大数量。
     * @return 成功执行的SQL语句数量。
     */
    public static int executeUpdate(final String sql,
            final List<Type> paramTypes,
            final List<Object[]> paramValues,
            final Connection connection,
            final int batchSize) throws SQLException {
        if ((paramTypes != null && paramValues == null) || (paramTypes == null && paramValues
                != null)) {
            throw new IllegalArgumentException(PARAMETER_ERROR_MESSAGE);
        }

        if (connection == null) {
            throw new IllegalArgumentException(
                    "Connection Object can not as null.");
        }

        PreparedStatement statement = connection.prepareStatement(sql);
        int bathSizeCurrent = (batchSize < 1) ? 1 : batchSize;
        int size = 0;

        if (paramTypes != null && paramValues != null) {
            List<Entry<Type, Object>> paramEntrys = new LinkedList<Entry<Type, Object>>();
            Entry<Type, Object> paramEntry = null;
            Object[] paramValue = null;

            for (int count = 0; count < paramValues.size(); count++) {
                paramEntrys.clear();
                paramValue = paramValues.get(count);

                if (paramValue.length != paramTypes.size()) {
                    throw new IllegalArgumentException(PARAMETER_ERROR_MESSAGE);
                }

                for (int valueCount = 0; valueCount < paramValue.length;
                        valueCount++) {
                    paramEntry = new SimpleEntry<Type, Object>(paramTypes.get(
                            valueCount), paramValue[valueCount]);
                    paramEntrys.add(paramEntry);
                }
                injectionParameters(statement, paramEntrys);
                statement.addBatch();

                if (count % bathSizeCurrent == 0) {
                    for (int updateValue : statement.executeBatch()) {
                        size += updateValue;
                    }
                }
            }
            for (int updateValue : statement.executeBatch()) {
                size += updateValue;
            }
        } else {
            size = statement.executeUpdate();
        }
        try {
            return size;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 执行查询SQL语句的方法。返回一个List列表，其中的每个元素都是一个Object[].
     * @param sql 需要执行的SQL。
     * @param params 执行SQL时注入的参数列表。
     * @param resultStyle 返回类型。
     * @param connection 数据库连接。
     * @return List实例。
     * @return
     */
    public static List<Object[]> executeQuery(
            final String sql,
            final List<Entry<Type, Object>> params,
            final List<Type> resultType,
            final Connection connection) {

        return null;
    }

    /**
     * 插入执行参数。
     * @param statement 包含预编译 SQL 语句PreparedStatement实例。
     * @param params 当前需要注入的参数列表。
     * @throws SQLException
     */
    private static void injectionParameters(PreparedStatement statement,
            List<Entry<Type, Object>> paramEntrys) throws SQLException {
        if (statement == null || paramEntrys == null || paramEntrys.isEmpty()) {
            return;
        }

        int count = 1;
        for (Entry<Type, Object> param : paramEntrys) {
            param.getKey().setValue(statement, count++, param.getValue());
        }
    }
}
