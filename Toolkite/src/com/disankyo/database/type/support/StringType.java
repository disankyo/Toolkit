package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * String数据类型
 * @version 1.00 2010-12-31 10:36:06
 * @since 1.6
 * @author disankyo
 */
public class StringType extends BaseType<String> {

    private static final Type<String> type = new StringType();

    public static Type<String> getStringType() {
        return type;
    }

    public String getValue(ResultSet result, int index) throws SQLException {
        return result.getString(index);
    }

    public String getValue(ResultSet result, String fieldName) throws
            SQLException {
        return result.getString(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, String value)
            throws SQLException {
        statement.setString(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            String value) throws SQLException {
        statement.setString(filedName, value);
    }

    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return String.class;
    }
}
