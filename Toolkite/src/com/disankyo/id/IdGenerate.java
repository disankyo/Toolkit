package com.disankyo.id;

import java.util.UUID;

/**
 * 实体对象生成id的方法
 * @version 1.00 2012-3-28 18:03:37
 * @since 1.6
 * @author allean
 */
public class IdGenerate {

    private IdGenerate(){}

    /**
     * 以UUID策略生成一个32长度的字符串
     * @return UUID长度128的字符串
     */
    public static String getUUIDString(){
        String id = UUID.randomUUID().toString();
        id = id.replace("-", "");
        return id;
    }

}
