package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Float数据类型
 * @version 1.00 2010-12-31 11:27:31
 * @since 1.6
 * @author disankyo
 */
public class FloatType extends BaseType<Float> {

    private static final Type<Float> type = new FloatType();

    public static Type<Float> getFolatType() {
        return type;
    }

    public Float getValue(ResultSet result, int index) throws SQLException {
        return result.getFloat(index);
    }

    public Float getValue(ResultSet result, String fieldName) throws
            SQLException {
        return result.getFloat(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, Float value)
            throws SQLException {
        statement.setFloat(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Float value) throws SQLException {
        statement.setFloat(filedName, value);
    }

    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return Float.class;
    }
}
