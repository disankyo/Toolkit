package com.disankyo.remote.nio;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

/**
 * 远程方法调用信息
 * @version 1.00 2012-4-6 11:34:59
 * @since 1.6
 * @author allean
 */
@SuppressWarnings("rawtypes")
public class MethodData implements Externalizable{

    private String name;        //方法名称
    private Object[] clazzs;    //方法参数类型列表
    private Class[] params;     //方法参数值列表
    private String serviceName; //远程服务名称

    public MethodData(String name,
            Object[] clazzs,
            Class[] params,
            String serviceName){
        this.name = name;
        if(clazzs != null){
            this.clazzs = Arrays.copyOf(clazzs, clazzs.length);
        }

        if(params != null){
            this.params = Arrays.copyOf(params, params.length);
        }
        this.serviceName = serviceName;
    }
    
    public Object[] getClazzs() {
        return clazzs;
    }

    public void setClazzs(Object[] clazzs) {
        this.clazzs = clazzs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class[] getParams() {
        return params;
    }

    public void setParams(Class[] params) {
        this.params = params;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(clazzs);
        out.writeObject(params);
        out.writeObject(serviceName);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        name = (String) in.readObject();
        clazzs = (Object[]) in.readObject();
        params = (Class[]) in.readObject();
        serviceName = (String) in.readObject();
    }


}
