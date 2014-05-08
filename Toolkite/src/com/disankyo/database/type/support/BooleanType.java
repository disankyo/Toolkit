package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Boolean类型的数据
 * @version 1.00 2010-12-31 10:58:13
 * @since 1.6
 * @author disankyo
 */
public class BooleanType extends BaseType<Boolean> {

    private static final Type<Boolean> type = new BooleanType();

    public static Type<Boolean> getBooleanType() {
        return type;
    }

    public Boolean getValue(ResultSet result, int index) throws SQLException {
        return result.getBoolean(index);
    }

    public Boolean getValue(ResultSet result, String fieldName) throws
            SQLException {
        return result.getBoolean(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, Boolean value)
            throws SQLException {
        statement.setBoolean(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Boolean value) throws SQLException {
        statement.setBoolean(filedName, value);
    }

    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return Boolean.class;
    }
}
