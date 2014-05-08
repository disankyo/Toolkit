package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Time数据类型
 * @version 1.00 2010-12-31 11:32:21
 * @since 1.6
 * @author disankyo
 */
public class TimeType extends BaseType<Time> {

    private static final Type<Time> type = new TimeType();

    public static Type<Time> getTimeType() {
        return type;
    }

    public Time getValue(ResultSet result, int index) throws SQLException {
        return result.getTime(index);
    }

    public Time getValue(ResultSet result, String fieldName) throws SQLException {
        return result.getTime(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, Time value)
            throws SQLException {
        statement.setTime(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Time value) throws SQLException {
        statement.setTime(filedName, value);
    }

    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return Time.class;
    }
}
