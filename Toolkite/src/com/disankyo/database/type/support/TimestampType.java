package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Timestamp数据类型
 * @version 1.00 2010-12-31 11:32:43
 * @since 1.6
 * @author disankyo
 */
public class TimestampType extends BaseType<Timestamp> {

    private static final Type<Timestamp> type = new TimestampType();

    public static Type<Timestamp> getTimestampType() {
        return type;
    }

    public Timestamp getValue(ResultSet result, int index) throws SQLException {
        return result.getTimestamp(index);
    }

    public Timestamp getValue(ResultSet result, String fieldName) throws
            SQLException {
        return result.getTimestamp(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, Timestamp value)
            throws SQLException {
        statement.setTimestamp(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Timestamp value) throws SQLException {
        statement.setTimestamp(filedName, value);
    }

    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return Timestamp.class;
    }
}
