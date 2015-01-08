package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Date数据类型
 * @version 1.00 2010-12-30 15:18:06
 * @since 1.6
 * @author disankyo
 */
public class DateType extends BaseType<Date> {

    private static final Type<Date> type = new DateType();

    public static Type<Date> getDateType() {
        return type;
    }

    public Date getValue(ResultSet result, int index) throws SQLException {
        return result.getDate(index);
    }

    public Date getValue(ResultSet result, String fieldName) throws SQLException {
        return result.getDate(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, Date value)
            throws SQLException {
        statement.setDate(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Date value) throws SQLException {
        statement.setDate(filedName, value);
    }

    @SuppressWarnings("rawtypes")
    public Class getTypeClass() {
        return type.getClass();
    }
}
