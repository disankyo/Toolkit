package com.disankyo.remote;

/**
 * 远程调用方法服务端抽象类
 * @version 1.00 2012-3-30 11:13:06
 * @since 1.6
 * @author allean
 */
public abstract class ServiceExporter extends Exporter{

    private Object target;     //目标对象
    private String serviceName;//服务名称

    /**
     * 获取服务名称
     * @return 服务名称
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 设置服务名称
     * @param serviceName 服务名称
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 获取目标对象
     * @return 目标对象
     */
    public Object getTarget() {
        return target;
    }

    /**
     * 设置目标对象
     * @param target 目标对象
     */
    public void setTarget(Object target) {
        this.target = target;
    }

    
}
