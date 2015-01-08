package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Double数据类型
 * @version 1.00 2010-12-31 11:17:48
 * @since 1.6
 * @author disankyo
 */
public class DoubleType extends BaseType<Double> {

    private static final Type<Double> type = new DoubleType();

    public static Type<Double> getDoubleType() {
        return type;
    }

    public Double getValue(ResultSet result, int index) throws SQLException {
        return result.getDouble(index);
    }

    public Double getValue(ResultSet result, String fieldName) throws
            SQLException {
        return result.getDouble(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, Double value)
            throws SQLException {
        statement.setDouble(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Double value) throws SQLException {
        statement.setDouble(filedName, value);
    }

    @SuppressWarnings("rawtypes")
    public Class getTypeClass() {
        return Double.class;
    }
}
