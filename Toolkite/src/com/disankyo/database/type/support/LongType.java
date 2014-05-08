package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Long数据类型
 * @version 1.00 2010-12-31 11:09:10
 * @since 1.6
 * @author disankyo
 */
public class LongType extends BaseType<Long> {

    private static final Type<Long> type = new LongType();

    public static Type<Long> getLongType() {
        return type;
    }

    public Long getValue(ResultSet result, int index) throws SQLException {
        return result.getLong(index);
    }

    public Long getValue(ResultSet result, String fieldName) throws SQLException {
        return result.getLong(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, Long value)
            throws SQLException {
        statement.setLong(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Long value) throws SQLException {
        statement.setLong(filedName, value);
    }

    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return Long.class;
    }
}
