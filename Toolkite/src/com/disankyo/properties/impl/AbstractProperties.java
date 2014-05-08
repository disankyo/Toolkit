package com.disankyo.properties.impl;

import com.disankyo.properties.Properties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;

/**
 * 
 * @version 1.00 2012-4-4 0:12:13
 * @since JDK 1.6
 * @author disankyo
 */
public abstract class AbstractProperties implements Properties{

    /**
     * 通过给定的属性配置文件路径来载入文件配置信息
     * @param url 配置文件的URL信息
     * @throws IOException
     */
    @Override
    public void load(URL url) throws IOException {
        doLoadProperties(url.getPath());
    }

    /**
     * 通过给定的属性文件来载入文件配置信息
     * @param file 属性文件
     * @throws IOException
     */
    @Override
    public void load(File file) throws IOException {
        doLoadProperties(file.getPath());
    }

    /**
     * 通过给定的属性文件路径来载入文件配置信息
     * @param path 属性文件路径
     * @throws IOException
     */
    @Override
    public void load(String path) throws IOException {
        doLoadProperties(path);
    }

    /**
     * 通过输入流加载属性文件
     * @param in 输入流
     * @throws IOException
     */
    @Override
    public void load(InputStream in) throws IOException{
        doLoadProperties(in);
    }

    /**
     * 通过路径加载属性文件由子类实现
     * @param filePath 属性文件路径
     * @throws IOException
     */
    public abstract void doLoadProperties(String filePath) throws IOException;

    /**
     * 通过输入流加载属性文件由子类实现
     * @param filePath 属性文件路径
     * @throws IOException
     */
    public abstract void doLoadProperties(InputStream in) throws IOException;

    @Override
    public int getIntValue(String key) {
        return Integer.parseInt(getValue(key));
    }

    @Override
    public BigDecimal getBigDecimalValue(String key) {
        return new BigDecimal(getValue(key));
    }

    @Override
    public BigInteger getBigInteger(String key) {
        return new BigInteger(getValue(key));
    }

    @Override
    public boolean getBooleanValue(String key) {
        return Boolean.valueOf(getValue(key));
    }

    @Override
    public byte getByteValue(String key) {
        return Byte.parseByte(getValue(key));
    }

    @Override
    public double getDoubleValue(String key) {
        return Double.parseDouble(getValue(key));
    }

    @Override
    public float getFloatValue(String key) {
        return Float.parseFloat(getValue(key));
    }

    @Override
    public long getLongValue(String key) {
        return Long.parseLong(getValue(key));
    }

    @Override
    public short getShortValue(String key) {
        return Short.parseShort(getValue(key));
    }

}
