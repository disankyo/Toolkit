package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Byte数据类型
 * @version 1.00 2010-12-31 10:46:46
 * @since 1.6
 * @author disankyo
 */
public class ByteType extends BaseType<Byte> {

    private static final Type<Byte> type = new ByteType();

    public static Type<Byte> getByteType() {
        return type;
    }

    public Byte getValue(ResultSet result, int index) throws SQLException {
        return result.getByte(index);
    }

    public Byte getValue(ResultSet result, String fieldName) throws SQLException {
        return result.getByte(fieldName);
    }

    public void setValue(PreparedStatement statement, int index, Byte value)
            throws SQLException {
        statement.setByte(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            Byte value) throws SQLException {
        statement.setByte(filedName, value);
    }

    @SuppressWarnings("unchecked")
    public Class getTypeClass() {
        return Byte.class;
    }
}
