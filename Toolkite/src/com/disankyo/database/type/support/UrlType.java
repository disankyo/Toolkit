package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * URL数据类型
 * @version 1.00 2010-12-31 11:33:08
 * @since 1.6
 * @author dianskyo
 */
public class UrlType extends BaseType<URL>{
    private static final Type<URL> type = new UrlType();

    public static Type<URL> getUrlType() {
        return type;
    }

    public URL getValue(ResultSet result, int index) throws SQLException {
        return result.getURL(index);
    }

    public URL getValue(ResultSet result, String fieldName) throws SQLException {
        return result.getURL(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, URL value)
            throws SQLException {
        statement.setURL(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            URL value) throws SQLException {
        statement.setURL(filedName, value);
    }

    @SuppressWarnings("rawtypes")
    public Class getTypeClass() {
        return URL.class;
    }
}
