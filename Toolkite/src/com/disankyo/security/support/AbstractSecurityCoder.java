package com.disankyo.security.support;

import com.disankyo.log.SyncLogUtil;
import com.disankyo.security.SecurityCoder;

import org.apache.commons.logging.LogFactory;

/**
 * 加密、解密接口的抽象实现
 * @version 1.00 2012-5-22 12:36:48
 * @since 1.6
 * @author allean
 */
public abstract class AbstractSecurityCoder implements SecurityCoder{

    protected final SyncLogUtil LOG = new SyncLogUtil(LogFactory.getLog(AbstractSecurityCoder.class));
    protected void checkData(String source){
        if(source == null){
            throw new IllegalArgumentException("The source is null.");
        }
    }
}
