package com.disankyo.properties.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @version 1.00 2012-4-4 0:17:16
 * @since JDK 1.6
 * @author disankyo
 */
public class CommonProperties extends AbstractProperties{

    private Properties pro;

    public CommonProperties(){
    	this.pro = new Properties();
    }

    public CommonProperties(Properties pro){
        this.pro = pro;
    }

    /**
     * 加载属性文件
     * @param filePath 属性文件的路径
     * @throws IOException
     */
    @Override
    public void doLoadProperties(String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        doLoadProperties(in);
    }

    /**
     * 加载属性文件
     * @param in 输入流
     * @throws IOException
     */
    @Override
    public void doLoadProperties(InputStream in) throws IOException {
        try{
            pro.load(in);
        } finally{
            in.close();
        }
    }
    
    @Override
    public String getValue(String key) {
        return pro.getProperty(key).trim();
    }

    @Override
    public List<String> valueList(String key) {
        Collection<Object> values = pro.values();

        List<String> valueList = new ArrayList<String>(values.size());
        for(Object value : values){
            valueList.add((String) value);
        }

        return valueList;
    }

    @Override
    public Set<String> keySet() {
        Set<Object> keys = pro.keySet();
        if(keys == null || keys.isEmpty()){
            return Collections.emptySet();
        }
        
        Set<String> result = new HashSet<String>();
        for(Object k : keys){
            result.add((String) k);
        }

        return result;
    }

}
