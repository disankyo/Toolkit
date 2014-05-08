package com.disankyo.properties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * 读取配置文件的统一接口
 * @version 1.00 2012-4-3
 * @since JDK 1.6
 * @author disankyo
 */
public interface Properties {

    public void load(URL url) throws IOException;

    public void load(File file) throws IOException;

    public void load(String path) throws IOException;

    public void load(InputStream in) throws IOException;

    public abstract String getValue(String key);

    public List<String> valueList(String key);

    public Set<String> keySet();

    public int getIntValue(String key);

    public long getLongValue(String key);

    public double getDoubleValue(String key);

    public float getFloatValue(String key);

    public byte getByteValue(String key);

    public short getShortValue(String key);

    public boolean getBooleanValue(String key);

    public BigInteger getBigInteger(String key);

    public BigDecimal getBigDecimalValue(String key);
}
