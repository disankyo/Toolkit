package com.disankyo.database;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个PreparedStatement的包装类，用于实现命名参数，
 * @version 1.00 2010-12-30 15:28:47
 * @since 1.6
 * @author disankyo
 */
public class NameParamPreparedStatement {

    /**
     * 被包装的preparedstatement
     */
    private PreparedStatement statement = null;
    /**
     * 命名参数和对应序号的键值对
     */
    private Map<String, Integer> indexMap = null;
    /**
     * 把命名参数SQL转换为标准SQL的正则表达式
     */
    private static final String repalceReg = "(\\:[-\\w]++)";

    /**
     * 将命名参数的SQL转换成标准的SQL，最后构造成实际的preparedStatement实例
     * @param connection Connection
     * @param nameSql 命名参数的sql
     * @throws SQLException
     */
    public NameParamPreparedStatement(Connection connection, String nameSql)
            throws SQLException {
        if (connection != null && nameSql != null && !nameSql.isEmpty()) {
            if (connection.isClosed()) {
                throw new SQLException("Connection has been closed...");
            }

            this.statement = connection.prepareStatement(convertedToStandardSqlStatement(
                    nameSql));
            indexMap = paseSql(nameSql);
        } else {
            throw new SQLException("SQL statement can not be null");
        }
    }

    /**
     * 将命名参数SQL语句中的参数解析出来，以参数名称和序号对应的哈希表返回
     * @param nameSql 带命名参数的SQL语句
     * @return 参数名称和对应序号的哈希表
     */
    private Map<String, Integer> paseSql(String nameSql) {
        StringBuilder sql = new StringBuilder(nameSql);
        char nowChar;
        boolean paramFlag = false;
        int index = 1;
        StringBuilder temp = new StringBuilder();
        Map<String, Integer> newMap = new HashMap<String, Integer>();

        for (int i = 0; i < sql.length(); i++) {
            nowChar = sql.charAt(i);
            if (nowChar == ':') {
                paramFlag = true;
            } else if (nowChar == ' ' || nowChar == ',' || nowChar == ')') {
                if (paramFlag) {
                    if (temp.length() > 0) {
                        newMap.put(temp.toString(), index++);
                        temp.delete(0, temp.length());
                        paramFlag = false;
                    }
                }
            } else {
                if (paramFlag) {
                    temp.append(nowChar);
                }
            }
        }

        return newMap;
    }

    /**
     * 把带命名参数的SQL转换为标准的SQL
     * @param nameSql 命名参数的sql
     * @return 标准的生sql
     */
    private String convertedToStandardSqlStatement(String nameSql) {
        return nameSql.replaceAll(repalceReg, "?");
    }

    /**
     * 获取命名参数对的序号
     * @param name 命名参数
     * @return 命名参数对应的序号
     */
    private int getIndex(String parameterName) throws SQLException {
        Integer index = indexMap.get(parameterName);
        if (index == null) {
            throw new SQLException("Did not find the name of the parameters.");
        }
        return index.intValue();
    }

    /**
     * 将指定参数设置为SQL NULL
     * @param parameterName 参数名称
     * @param sqlType java.sql.Types 中定义的 SQL 类型代码
     * @throws SQLException
     */
    public void setNull(String parameterName, int sqlType) throws SQLException {
        this.statement.setNull(getIndex(parameterName), sqlType);
    }

    /**
     * 将指定参数设置为 SQL NULL。这种形式的 setNull 方法应该用于用户定义的类型或 REF 类型参数。
     * 用户定义类型的示例有：STRUCT、DISTINCT、JAVA_OBJECT 和指定的数组类型。
     *
     * 注：为了便于移植，在指定 NULL 用户定义参数或 REF 参数时，应用程序必须提供 SQL 类型代码和完全限定的 SQL 类型名称。
     * 对于户定义类型，名称是参数本身的类型名称。对于 REF 参数，名称是所引用类型的类型名称。
     * 如果 JDBC 驱动程序不需要类型代码和类型名称信息，则可以忽略这些信息。尽管此方法是供用户定义的参数和 Ref 参数使用的，
     * 但也可以使用其设置任何 JDBC 类型的 null 参数。如果该参数没有用户定义的类型或 REF 类型，则忽略给定的 typeName。
     * @param parameterName 参数名称
     * @param sqlType 取自 java.sql.Types 的一个值
     * @param typeName SQL 用户定义类型的完全限定名称；如果该参数不是用户定义类型或 REF，则忽略它
     * @throws SQLException
     */
    public void setNull(String parameterName, int sqlType, String typeName)
            throws SQLException {
        this.statement.setNull(getIndex(parameterName), sqlType, typeName);
    }

    /**
     * 将指定参数设置为给定 Java boolean 值。在将此值发送到数据库时，
     * 驱动程序将它转换成一个 SQL BIT 或 BOOLEAN 值。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setBoolean(String parameterName, boolean x) throws SQLException {
        this.statement.setBoolean(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 Java byte 值。在将此值发送到数据库时，
     * 驱动程序将它转换成一个 SQL TINYINT 值。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setByte(String parameterName, byte x) throws SQLException {
        this.statement.setByte(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 Java byte 数组。在将此值发送给数据库时，驱动程序将它转换成一个
     * SQL VARBINARY 或 LONGVARBINARY 值（取决于该参数相对于驱动程序在 VARBINARY 值上的限制的大小）。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setBytes(String parameterName, byte x[]) throws SQLException {
        this.statement.setBytes(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 Java short 值。在将此值发送给数据库时，驱动程序将它转换成一个
     * SQL SMALLINT
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setShort(String parameterName, short x) throws SQLException {
        this.statement.setShort(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 Java int 值。在将此值发送给数据库时，驱动程序将它转换成一个SQL INTEGER值 。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setInt(String parameterName, int x) throws SQLException {
        this.statement.setInt(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 Java long 值。在将此值发送给数据库时，驱动程序将它转换成一个SQL BIDINT值 。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setLong(String parameterName, long x) throws SQLException {
        this.statement.setLong(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 Java float 值。在将此值发送给数据库时，驱动程序将它转换成一个SQL FLOAT值 。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setFloat(String parameterName, float x) throws SQLException {
        this.statement.setFloat(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 Java double 值。在将此值发送给数据库时，驱动程序将它转换成一个SQL DOUBLE值 。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setDouble(String parameterName, double x) throws SQLException {
        this.statement.setDouble(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 Java BigDecimal 值。在将此值发送给数据库时，驱动程序将它转换成一个SQL NUMBERIC 。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setBigDecimal(String parameterName, BigDecimal x) throws
            SQLException {
        this.statement.setBigDecimal(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 Java String 值。在将此值发送给数据库时，驱动程序将它转换成一个
     * SQL VARCHAR 或 LONGVARCHAR 值（取决于该参数相对于驱动程序在 VARCHAR 值上的限制的大小）。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setString(String parameterName, String x) throws SQLException {
        this.statement.setString(getIndex(parameterName), x);
    }

    /**
     * 使用运行应用程序的虚拟机的默认时区将指定参数设置为给定 java.sql.Date 值。
     * 在将此值发送到数据库时，驱动程序将它转换成一个 SQL DATE 值。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setDate(String parameterName, Date x) throws SQLException {
        this.statement.setDate(getIndex(parameterName), x);
    }

    /**
     * 使用给定的 Calendar 对象将指定参数设置为给定 java.sql.Date 值。
     * 驱动程序使用 Calendar 对象构造一个 SQL DATE 值，该值随后被驱动程序发送给数据库。
     * 利用 Calendar 对象，驱动程序可以在考虑自定义时区的情况下计算日期。
     * 如果没有指定任何 Calendar 对象，那么驱动程序将使用默认时区，该时区是运行应用程序的虚拟机所在的时区。
     * @param parameterName 参数名称。
     * @param x 参数值.
     * @param cal 驱动程序将用来构造日期的 Calendar 对象。
     * @throws java.sql.SQLException
     */
    public void setDate(String parameterName, Date x, Calendar cal) throws
            SQLException {
        this.statement.setDate(getIndex(parameterName), x, cal);
    }

    /**
     * 将指定参数设置为给定 java.sql.Time 值。在将此值发送到数据库时，驱动程序将它转换成一个 SQL TIME 值。
     * @param parameterName 参数名称。
     * @param x 参数值。
     * @throws java.sql.SQLException
     */
    public void setTime(String parameterName, Time x) throws SQLException {
        this.statement.setTime(getIndex(parameterName), x);
    }

    /**
     * 使用给定的 Calendar 对象将指定参数设置为给定 java.sql.Time 值。驱动程序使用 Calendar 对象
     * 构造一个 SQL TIME 值，该值随后被驱动程序发送给数据库。利用 Calendar 对象，
     * 驱动程序可以在考虑自定义时区的情况下计算时间。如果没有指定任何 Calendar 对象，
     * 那么驱动程序将使用默认时区，该时区是运行应用程序的虚拟机所在的时区。
     * @param parameterName 参数名称。
     * @param x 参数值。
     * @param cal 驱动程序将用来构造日期的 Calendar 对象。
     * @throws java.sql.SQLException
     */
    public void setTime(String parameterName, Time x, Calendar cal) throws
            SQLException {
        this.statement.setTime(getIndex(parameterName), x, cal);
    }

    /**
     * 将指定参数设置为给定 java.sql.Timestamp 值。在将此值发送到数据库时，驱动程序将它转换成一个 SQL TIMESTAMP 值。
     * @param parameterName 参数名称。
     * @param x 参数值。
     * @throws java.sql.SQLException
     */
    public void setTimestamp(String parameterName, Timestamp x) throws
            SQLException {
        this.statement.setTimestamp(getIndex(parameterName), x);
    }

    /**
     * 使用给定的 Calendar 对象将指定参数设置为给定 java.sql.Timestamp 值。
     * 驱动程序使用 Calendar 对象构造一个 SQL TIMESTAMP 值，该值随后被驱动程序发送给数据库。
     * 利用 Calendar 对象，驱动程序可以在考虑自定义时区的情况下计算时间戳。
     * 如果没有指定任何 Calendar 对象，那么驱动程序将使用默认时区，该时区是运行应用程序的虚拟机所在的时区。
     * @param parameterName 参数名称。
     * @param x 参数值。
     * @param cal 驱动程序将用来构造日期的 Calendar 对象。
     * @throws java.sql.SQLException
     */
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
            throws SQLException {
        this.statement.setTimestamp(getIndex(parameterName), x, cal);
    }

    /**
     * 将指定参数设置为给定输入流。在将一个非常大的 ASCII 值输入到 LONGVARCHAR 参数时，
     * 通过 java.io.InputStream 发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * JDBC 驱动程序将执行从 ASCII 到数据库 char 格式的任何必要转换。 
     * @param parameterName 参数名称。
     * @param x 包含二进制参数值的 Java 输入流。
     * @throws java.sql.SQLException
     */
    public void setAsciiStream(String parameterName, InputStream x) throws
            SQLException {
        this.statement.setAsciiStream(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定输入流。在将一个非常大的 ASCII 值输入到 LONGVARCHAR 参数时，
     * 通过 java.io.InputStream 发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * JDBC 驱动程序将执行从 ASCII 到数据库 char 格式的任何必要转换。
     * @param parameterName 参数名称。
     * @param x 包含二进制参数值的 Java 输入流。
     * @param length 流中的字节数。
     * @throws SQLException
     */
    public void setAsciiStream(String parameterName, InputStream x, int length)
            throws SQLException {
        this.statement.setAsciiStream(getIndex(parameterName), x, length);
    }

    /**
     * 将指定参数设置为给定输入流。在将一个非常大的 ASCII 值输入到 LONGVARCHAR 参数时，
     * 通过 java.io.InputStream 发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * JDBC 驱动程序将执行从 ASCII 到数据库 char 格式的任何必要转换。
     * @param parameterName 参数名称。
     * @param x 包含二进制参数值的 Java 输入流。
     * @param length 流中的字节数。
     * @throws SQLException
     */
    public void setAsciiStream(String parameterName, InputStream x, long length)
            throws SQLException {
        this.statement.setAsciiStream(getIndex(parameterName), x, length);

    }

    /**
     * 将指定参数设置为给定输入流，该输入流将具有给定字节数。在将一个非常大的 Unicode 值输入到 LONGVARCHAR 参数时，
     * 通过 java.io.InputStream 对象发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * JDBC 驱动程序将执行从 Unicode 到数据库 char 格式的任何必要转换。根据 Java 虚拟机规范中的定义，
     * Unicode 流的字节格式必须是 Java UTF-8。
     * 
     * 注：此流对象既可以是一个标准 Java 流对象，也可以是实现标准接口的用户自己的子类。
     * @param parameterName 参数名称。
     * @param x 包含二进制参数值的 Java 输入流。
     * @param length 流中的字节数。
     * @deprecated 
     */
    public void setUnicodeStream(String parameterName, InputStream x, int length)
            throws SQLException {
        this.statement.setUnicodeStream(getIndex(parameterName), x, length);
    }

    /**
     * 将指定参数设置为给定输入流。在将一个非常大的二进制值输入到 LONGVARBINARY 参数时，
     * 通过 java.io.InputStream 对象发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * @param parameterName 参数名称。
     * @param x 包含二进制参数值的 Java 输入流。
     * @throws SQLException
     */
    public void setBinaryStream(String parameterName, InputStream x) throws
            SQLException {
        this.statement.setBinaryStream(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定输入流。在将一个非常大的二进制值输入到 LONGVARBINARY 参数时，
     * 通过 java.io.InputStream 对象发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * @param parameterName 参数名称。
     * @param x 包含二进制参数值的 Java 输入流。
     * @param length 流中的字节数
     * @throws SQLException
     */
    public void setBinaryStream(String parameterName, InputStream x, int length)
            throws SQLException {
        this.statement.setBinaryStream(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定输入流。在将一个非常大的二进制值输入到 LONGVARBINARY 参数时，
     * 通过 java.io.InputStream 对象发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * @param parameterName 参数名称。
     * @param x 包含二进制参数值的 Java 输入流。
     * @param length 流中的字节数
     * @throws SQLException
     */
    public void setBinaryStream(String parameterName, InputStream x, long length)
            throws SQLException {
        this.statement.setBinaryStream(getIndex(parameterName), x, length);
    }

    /**
     * 立即清除当前参数值。
     * 通常参数值对语句的重复使用仍然有效。
     * 设置一个参数值会自动清除其以前的值。
     * 不过，在某些情况下，直接释放当前参数值使用的资源也是很有用的；
     * 这可以通过调用 clearParameters 方法实现。
     * @throws SQLException
     */
    public void clearParameters() throws SQLException {
        this.statement.clearParameters();
    }

    /**
     * 使用给定对象设置指定参数的值。
     * 第二个参数必须是 Object 类型；
     * 所以，应该对内置类型使用 java.lang 的等效对象。
     * JDBC 规范指定了一个从 Java Object 类型到 SQL 类型的标准映射关系。
     * 在发送到数据库之前，给定参数将被转换为相应 SQL 类型。
     *
     * 注意，通过使用特定于驱动程序的 Java 类型，此方法可用于传递特定于数据库的抽象数据类型。
     * 如果对象是实现 SQLData 接口的类，则 JDBC 驱动程序应该调
     * 用 SQLData.writeSQL 方法将它写入 SQL 数据流中。另一方面，
     * 如果该对象是实现 Ref、Blob、Clob、NClob、Struct、java.net.URL、RowId、SQLXML
     * 或 Array 的类，则驱动程序应该将它作为相应 SQL 类型的值传递给数据库。
     *
     * 注：并非所有的数据库都允许将非类型 Null 发送给后端。
     * 为了获得最大的可移植性，应该使用 setNull 或
     * setObject(int parameterIndex, Object x, int sqlType) 方法替代 setObject(int parameterIndex, Object x)。
     *
     * 注：如果出现混淆，例如，如果该对象是实现多个上述指定接口的类，则此方法抛出异常。
     * @param parameterName 参数名称。
     * @param x 包含输入参数值的对象。
     */
    public void setObject(String parameterName, Object x) throws SQLException {
        this.statement.setObject(getIndex(parameterName), x);
    }

    /**
     * 使用给定对象设置指定参数的值。除了假定 scale 为 0，此方法类似于上面的 setObject(String,int) 方法。
     * @param parameterName 参数名称。
     * @param x 包含输入参数值的对象。
     * @param targetSqlType 将发送给数据库的 SQL 类型（定义于 java.sql.Types 中）
     * @throws SQLException
     */
    public void setObject(String parameterName, Object x, int targetSqlType)
            throws SQLException {
        this.statement.setObject(getIndex(parameterName), x);
    }

    /**
     * 使用给定对象设置指定参数的值。第二个参数必须是一个对象类型；
     * 对于整数值，应该使用 java.lang 的等效对象。 如果第二个参数是一个 InputStream，
     * 则该流必须包含 scaleOrLength 指定的字节数。如果第二个参数是一个 Reader，
     * 则该 Reader 必须包含 scaleOrLength 指定的字符数。如果这些条件不满足，
     * 则驱动程序在执行准备好的语句时将生成一个 SQLException。在发送到数据库之前，
     * 给定 Java 对象将被转换为给定 targetSqlType。 如果对象具有自定义映射关系
     * （属于实现 SQLData 接口的类），那么 JDBC 驱动程序应该调用 SQLData.writeSQL
     * 方法将它写入 SQL 数据流。另一方面，如果该对象是实现 Ref、Blob、Clob、NClob、Struct、java.net.URL
     * 或 Array 的类，那么驱动程序应该将它作为相应 SQL 类型的值传递给数据库。
     *
     * 注意，此方法可用于传递特定于数据库的抽象数据类型。
     * @param parameterName 参数名称。
     * @param x 包含输入参数值的对象
     * @param targetSqlType 将发送给数据库的 SQL 类型（定义于 java.sql.Types 中）。scale 参数可以进一步限定此类型。
     * @param scaleOrLength 对于 java.sql.Types.DECIMAL 或 java.sql.Types.NUMERIC 类型，
     * 此值是小数点后的位数。对于 Java Object 类型 InputStream 和 Reader，
     * 此值是流或 Reader 中数据的长度。对于所有其他类型，忽略此值。
     * @throws SQLException
     */
    public void setObject(String parameterName, Object x, int targetSqlType,
            int scaleOrLength) throws SQLException {
        this.statement.setObject(getIndex(parameterName), x, targetSqlType,
                scaleOrLength);
    }

    /**
     * 在此 NameParamPreparedStatement 对象中执行 SQL 语句，该语句可以是任何种类的SQL语句。
     * 一些预处理过的语句返回多个结果，execute 方法处理这些复杂的语句，executeQuery
     * 和 executeUpdate 处理形式更简单的语句。
     * execute 方法返回一个 boolean 值，指示第一个结果的形式。
     * 必须调用 getResultSet 或 getUpdateCount 方法获取该结果，必须调用 getMoreResults 获取任何后续结果。
     * @return 如果第一个结果是 ResultSet 对象，则返回 true；如果第一个结果是更新计数或者没有结果，则返回 false。
     * @throws SQLException
     */
    public void execute() throws SQLException {
        this.statement.execute();
    }

    /**
     * 在此 NameParamPreparedStatement 对象中执行 SQL 查询，并返回该查询生成的 ResultSet 对象。
     * @return 包含该查询生成的数据的 ResultSet 对象；不会返回 null。
     * @throws SQLException
     */
    public ResultSet executeQuery() throws SQLException {
        return this.statement.executeQuery();
    }

    /**
     * 在此 PreparedStatement 对象中执行 SQL 语句，
     * 该语句必须是一个 SQL 数据操作语言（Data Manipulation Language，DML）语句，
     * 比如 INSERT、UPDATE 或 DELETE 语句；或者是无返回内容的 SQL 语句，比如 DDL 语句。
     * @return (1) SQL 数据操作语言 (DML) 语句的行数 (2) 对于无返回内容的 SQL 语句，返回 0。
     * @throws SQLException
     */
    public int executeUpdate() throws SQLException {
        return this.statement.executeUpdate();
    }

    /**
     * 将一组参数添加到此 PreparedStatement 对象的批处理命令中。
     * @throws SQLException
     */
    public void addBatch() throws SQLException {
        this.statement.addBatch();
    }

    /**
     * 获取包含有关 ResultSet 对象列信息的 ResultSetMetaData 对象，ResultSet 对象将在执行此 PreparedStatement 对象时返回。
     * 因为 PreparedStatement 对象被预编译，所以不必执行就可以知道它将返回的 ResultSet 对象。
     * 因此，可以对 PreparedStatement 对象调用 getMetaData 方法，
     * 而不必等待执行该对象，然后再对返回的 ResultSet 对象调用 ResultSet.getMetaData 方法。
     *
     * 注：对于某些缺乏底层 DBMS 支持的驱动程序，使用此方法开销可能很大。
     *
     * @return ResultSet 对象列的描述；如果驱动程序无法返回一个 ResultSetMetaData 对象，则返回 null。
     * @throws SQLException
     */
    public ResultSetMetaData getMetaData() throws SQLException {
        return this.statement.getMetaData();
    }

    /**
     * 获取此 PreparedStatement 对象的参数的编号、类型和属性。
     * @return 一个 ParameterMetaData 对象，它包含有关此 PreparedStatement 对象的每个参数标记的编号、类型和属性的信息。
     * @throws SQLException
     */
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return this.statement.getParameterMetaData();
    }

    /**
     * 将指定参数设置为给定 Reader 对象。在将一个非常大的 UNICODE 值输入到 LONGVARCHAR 参数时，
     * 通过 java.io.Reader 对象发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * JDBC 驱动程序将执行从 UNICODE 到数据库 char 格式的任何必要转换。
     * @param parameterName 参数名称
     * @param reader 包含 Unicode 数据的 java.io.Reader 对象。
     * @throws SQLException
     */
    public void setCharacterStream(String parameterName, Reader reader) throws
            SQLException {
        this.statement.setCharacterStream(getIndex(parameterName), reader);
    }

    /**
     * 将给定参数设置为给定 Reader 对象，该对象具有给定字符数长度。
     * 在将一个非常大的 UNICODE 值输入到 LONGVARCHAR 参数时，通过 java.io.Reader
     * 对象发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * JDBC 驱动程序将执行从 UNICODE 到数据库 char 格式的任何必要转换。
     *
     * 注：此流对象既可以是一个标准 Java 流对象，也可以是实现标准接口的用户自己的子类。
     *
     * @param parameterName 参数名称。
     * @param reader 包含 Unicode 数据的 java.io.Reader 对象。
     * @param length 流中的字符数。
     * @throws SQLException
     */
    public void setCharacterStream(String parameterName, Reader readerm,
            int length) throws SQLException {
        this.statement.setCharacterStream(getIndex(parameterName), readerm,
                length);
    }

    /**
     * 将给定参数设置为给定 Reader 对象，该对象具有给定字符数长度。
     * 在将一个非常大的 UNICODE 值输入到 LONGVARCHAR 参数时，通过 java.io.Reader
     * 对象发送它可能更为实际。将根据需要从流中读取数据，一直读取到文件末尾。
     * JDBC 驱动程序将执行从 UNICODE 到数据库 char 格式的任何必要转换。
     *
     * 注：此流对象既可以是一个标准 Java 流对象，也可以是实现标准接口的用户自己的子类。
     *
     * @param parameterName 参数名称。
     * @param reader 包含 Unicode 数据的 java.io.Reader 对象。
     * @param length 流中的字符数。
     * @throws SQLException
     */
    public void setCharacterStream(String parameterName, Reader reader,
            long length) throws SQLException {
        this.statement.setCharacterStream(getIndex(parameterName), reader,
                length);
    }

    /**
     * 将指定参数设置为给定 REF(<structured-type>) 值。在将此值发送到数据库时，
     * 驱动程序将它转换成一个 SQL REF 值。
     * @param parameterName 参数名称
     * @param ref 一个 SQL REF 值。
     * @throws SQLException
     */
    public void setRef(String parameterName, Ref ref) throws SQLException {
        this.statement.setRef(getIndex(parameterName), ref);
    }

    /**
     * 将指定参数设置为给定 java.sql.Blob 对象。在将此对象发送到数据库时，
     * 驱动程序将它转换成一个 SQL BLOB 值。
     * @param parameterName 参数名称
     * @param blob 一个映射 SQL BLOB 值的 Blob 对象
     * @throws SQLException
     */
    public void setBlob(String parameterName, Blob blob) throws SQLException {
        this.statement.setBlob(getIndex(parameterName), blob);
    }

    /**
     * 将指定参数设置为 InputStream 对象。此方法不同于 setBinaryStream (int, InputStream) 方法，
     * 因为它会通知驱动程序应该将参数值作为 BLOB 发送给服务器。在使用 setBinaryStream 方法时，
     * 驱动程序可能必须做一些额外的工作，以确定应该将参数数据作为 LONGVARBINARY 还是 BLOB 发送给服务器。
     * 注：查询 JDBC 驱动程序文档，以确定使用带 length 参数的 setBlob 是否更有效。
     * @param parameterName 参数名称
     * @param inputStream 包含用于设置参数值的数据的对象。
     * @throws SQLException
     */
    public void setBlob(String parameterName, InputStream inputStream) throws
            SQLException {
        this.statement.setBlob(getIndex(parameterName), inputStream);
    }

    /**
     * 将指定参数设置为 InputStream 对象。Inputstream 必须包含 length 指定的字符数，
     * 否则在执行 PreparedStatement 时将生成一个 SQLException。
     * 此方法不同于 setBinaryStream (int, InputStream, int) 方法，
     * 因为它会通知驱动程序应该将参数值作为 BLOB 发送给服务器。在使用 setBinaryStream 方法时，
     * 驱动程序可能必须做一些额外的工作，以确定应该将参数数据作为 LONGVARBINARY 还是 BLOB 发送给服务器。
     * @param parameterName 参数名称
     * @param inputStream 包含用于设置参数值的数据的对象。
     * @param length 参数数据中的字节数。
     * @throws SQLException
     */
    public void setBlob(String parameterName, InputStream inputStream,
            long length) throws SQLException {
        this.statement.setBlob(getIndex(parameterName), inputStream, length);
    }

    /**
     * 将指定参数设置为给定 java.sql.Clob 对象。在将此对象发送到数据库时，驱动程序将它转换成一个 SQL CLOB 值。 
     * @param parameterName 参数名称
     * @param clob 一个映射 SQL Clob 值的 Clob 对象
     * @throws SQLException
     */
    public void setClob(String parameterName, Clob clob) throws SQLException {
        this.statement.setClob(getIndex(parameterName), clob);
    }

    /**
     * 将指定参数设置为 Reader 对象。此方法不同于 setCharacterStream (int, Reader) 方法，
     * 因为它会通知驱动程序应该将参数值作为 CLOB 发送给服务器。在使用 setCharacterStream 方法时，
     * 驱动程序可能必须做一些额外的工作，以确定应该将参数数据作为 LONGVARCHAR 还是 CLOB 发送给服务器。
     * 注：查询 JDBC 驱动程序文档，以确定使用带 length 参数的 setClob 是否更有效。
     * @param parameterName 参数名称
     * @param reader 包含用于设置参数值的数据的对象
     * @throws SQLException
     */
    public void setClob(String parameterName, Reader reader) throws SQLException {
        this.statement.setClob(getIndex(parameterName), reader);
    }

    /**
     * 将指定参数设置为 Reader 对象。Reader 必须包含 length 指定的字符数，否则在执行 PreparedStatement
     * 时将生成一个 SQLException。此方法不同于 setCharacterStream (int, Reader, int) 方法，
     * 因为它会通知驱动程序应该将参数值作为 CLOB 发送给服务器。在使用 setCharacterStream 方法时，
     * 驱动程序可能必须做一些额外的工作，以确定应该将参数数据作为 LONGVARCHAR 还是 CLOB 发送给服务器。
     * @param parameterName 参数名称
     * @param reader 包含用于设置参数值的数据的对象
     * @param length 参数数据中的字符数
     * @throws SQLException
     */
    public void setClob(String parameterName, Reader reader, long length) throws
            SQLException {
        this.statement.setClob(getIndex(parameterName), reader, length);
    }

    /**
     * 将指定参数设置为给定 java.net.URL 值。在将此值发送到数据库时，驱动程序将它转换成一个 SQL DATALINK 值。
     * @param parameterName 参数名称
     * @param x 要设置的 java.net.URL 对象
     * @throws SQLException
     */
    public void setURL(String parameterName, URL x) throws SQLException {
        this.statement.setURL(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 java.sql.RowId 对象。在将此对象发送到数据库时，驱动程序将它转换成一个 SQL ROWID 值。
     * @param parameterName 参数名称
     * @param x 参数值
     * @throws SQLException
     */
    public void setRowId(String parameterName, RowId x) throws SQLException {
        this.statement.setRowId(getIndex(parameterName), x);
    }

    /**
     * 将指定参数设置为给定 String 对象。在将此对象发送给数据库时，驱动程序将它转换成一个
     * SQL NCHAR、NVARCHAR 或 LONGNVARCHAR 值（取决于该参数相对于驱动程序在 NVARCHAR 值上的限制的大小）。
     * @param parameterName 参数名称
     * @param value 参数值
     * @throws SQLException
     */
    public void setNString(String parameterName, String value) throws
            SQLException {
        this.statement.setNString(getIndex(parameterName), value);
    }

    /**
     * 将指定参数设置为 Reader 对象。Reader 将读取数据，直到到达文件末尾。驱动程序执行从 Java 字符格式到数据库中国家字符集的必要转换。
     * 注：此流对象既可以是一个标准 Java 流对象，也可以是实现标准接口的用户自己的子类。
     * 注：查询 JDBC 驱动程序文档，以确定使用带 length 参数的 setNCharacterStream 是否更有效。
     * @param parameterName 参数名称
     * @param reader 参数值
     * @throws SQLException
     */
    public void setNCharacterStream(String parameterName, Reader reader) throws
            SQLException {
        this.statement.setNCharacterStream(getIndex(parameterName), reader);
    }

    /**
     * 将指定参数设置为 Reader 对象。Reader 将读取数据，直到到达文件末尾。驱动程序执行从 Java 字符格式到数据库中国家字符集的必要转换。
     * @param parameterName 参数名称
     * @param reader 参数值
     * @param length 参数数据中的字符数。 
     * @throws SQLException
     */
    public void setNCharacterStream(String parameterName, Reader reader,
            long length) throws SQLException {
        this.statement.setNCharacterStream(getIndex(parameterName), reader,
                length);
    }

    /**
     * 将指定参数设置为 java.sql.NClob 对象。在将此对象发送到数据库时，驱动程序将它转换成一个 SQL NCLOB 值。
     * @param parameterName 参数名称
     * @param nClob 参数值
     * @throws SQLException
     */
    public void setNClob(String parameterName, NClob nClob) throws SQLException {
        this.statement.setNClob(getIndex(parameterName), nClob);
    }

    /**
     * 将指定参数设置为 InputStream 对象。此方法不同于 setBinaryStream (int, InputStream) 方法，
     * 因为它会通知驱动程序应该将参数值作为 BLOB 发送给服务器。在使用 setBinaryStream 方法时，
     * 驱动程序可能必须做一些额外的工作，以确定应该将参数数据作为 LONGVARBINARY 还是 BLOB 发送给服务器。
     * 注：查询 JDBC 驱动程序文档，以确定使用带 length 参数的 setBlob 是否更有效。
     * @param parameterName 参数名称
     * @param reader 包含用于设置参数值的数据的对象。
     * @throws SQLException
     */
    public void setNClob(String parameterName, Reader reader) throws
            SQLException {
        this.statement.setNClob(getIndex(parameterName), reader);
    }

    /**
     * 将指定参数设置为 Reader 对象。Reader 必须包含 length 指定的字符数，否则在执行 PreparedStatement
     * 时将生成一个 SQLException。此方法不同于 setCharacterStream (int, Reader, int) 方法，
     * 因为它会通知驱动程序应该将参数值作为 NCLOB 发送给服务器。在使用 setCharacterStream 方法时，
     * 驱动程序可能必须做一些额外的工作，以确定应该将参数数据作为 LONGNVARCHAR 还是 NCLOB 发送给服务器。
     * @param parameterName
     * @param reader 包含用于设置参数值的数据的对象。
     * @param length 参数数据中的字符数。
     * @throws SQLException
     */
    public void setNClob(String parameterName, Reader reader, long length)
            throws SQLException {
        this.statement.setNClob(getIndex(parameterName), reader, length);
    }

    /**
     * 立即释放此 Statement 对象的数据库和 JDBC 资源，而不是等待该对象自动关闭时发生此操作。
     * 一般来说，使用完后立即释放资源是一个好习惯，这样可以避免对数据库资源的占用。
     * 在已经关闭的 Statement 对象上调用 close 方法无效。
     * 注：关闭 Statement 对象时，还将同时关闭其当前的 ResultSet 对象（如果有）。
     * @throws SQLException
     */
    public void close() throws SQLException {
        this.statement.close();
    }
}
