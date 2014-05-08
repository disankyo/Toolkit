package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Byte数组的数据类型
 * @version 1.00 2010-12-31 10:51:01
 * @since 1.6
 * @author disankyo
 */
public class BytesType extends BaseType<byte[]> {

    private static final Type<byte[]> type = new BytesType();

    public static Type<byte[]> getBytesType() {
        return type;
    }

    public byte[] getValue(ResultSet result, int index) throws SQLException {
        return result.getBytes(index);
    }

    public byte[] getValue(ResultSet result, String fieldName) throws
            SQLException {
        return result.getBytes(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, byte[] value)
            throws SQLException {
        statement.setBytes(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            byte[] value) throws SQLException {
        statement.setBytes(filedName, value);
    }
    
    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return Byte[].class;
    }
}
