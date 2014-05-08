package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object数据类型
 * @version 1.00 2010-12-31 11:32:58
 * @since 1.6
 * @author disankyo
 */
public class ObjectType extends BaseType<Object> {

    private static final Type<Object> type = new ObjectType();

    public static Type<Object> getObjectType() {
        return type;
    }

    public Object getValue(ResultSet result, int index) throws SQLException {
        return result.getObject(index);
    }

    public Object getValue(ResultSet result, String fieldName) throws
            SQLException {
        return result.getObject(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, Object value)
            throws SQLException {
        statement.setObject(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Object value) throws SQLException {
        statement.setObject(filedName, value);
    }

    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return Object.class;
    }
}
