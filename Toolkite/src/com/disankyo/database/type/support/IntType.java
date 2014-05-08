package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Integer数据类型
 * @version 1.00 2010-12-31 11:04:53
 * @since 1.6
 * @author disankyo
 */
public class IntType extends BaseType<Integer> {

    private static final Type<Integer> type = new IntType();

    public static Type<Integer> getIntType() {
        return type;
    }

    public Integer getValue(ResultSet result, int index) throws SQLException {
        return Integer.valueOf(result.getInt(index));
    }

    public Integer getValue(ResultSet result, String fieldName) throws
            SQLException {
        return Integer.valueOf(result.getInt(fieldName));
    }

    public void setValue(PreparedStatement statement, int index, Integer value)
            throws SQLException {
        statement.setInt(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Integer value) throws SQLException {
        statement.setInt(filedName, value);
    }

    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return Integer.class;
    }
}
