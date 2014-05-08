package com.disankyo.security;

/**
 * 加密、解密的通一接口
 * @version 1.00 2012-5-22 12:08:54
 * @since 1.6
 * @author allean
 */
public interface SecurityCoder {

    /**
     * 对给定的数据加密,如果加密的数据为空或null,则抛出IllegalArgumentException
     * @param  source 需要加密的数据
     * @return 加密后的数据
     * @throws EncryptException 加密异常
     * @throws IllegalArgumentException 非法参数异常
     */
    public String encrypt(String source) throws EncryptException, IllegalArgumentException;

    /**
     * 把已经加密的数据进行解密,如果解密的数据为空或null,则抛出IllegalArgumentException
     * @param  source 需要解密的数据
     * @return 解密后的数据
     * @throws DecryptException 解密异常
     * @throws IllegalArgumentException 非法参数异常
     */
    public String decrypt(String source) throws DecryptException, IllegalArgumentException;
}
