package com.disankyo.database.type.support;

import com.disankyo.database.NameParamPreparedStatement;
import com.disankyo.database.type.Type;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @version 1.00 2010-12-31 10:41:17
 * @since 1.6
 * @author disankyo
 */
public class BigDecimalType extends BaseType<BigDecimal> {

    private static final Type<BigDecimal> type = new BigDecimalType();

    public static Type<BigDecimal> getBigDecimalType() {
        return type;
    }

    public BigDecimal getValue(ResultSet result, int index) throws SQLException {
        return result.getBigDecimal(index);
    }

    public BigDecimal getValue(ResultSet result, String fieldName) throws
            SQLException {
        return result.getBigDecimal(fieldName);
    }

    public void setValue(PreparedStatement statement, int index,
            BigDecimal value) throws SQLException {
        statement.setBigDecimal(index, value);
    }

    public void setValue(NameParamPreparedStatement statement, String filedName,
            BigDecimal value) throws SQLException {
        statement.setBigDecimal(filedName, value);
    }

    @SuppressWarnings("unchecked")
	public Class getTypeClass() {
        return BigDecimal.class;
    }
}
