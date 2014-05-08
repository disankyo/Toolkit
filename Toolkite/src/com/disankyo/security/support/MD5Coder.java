package com.disankyo.security.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.disankyo.security.DecryptException;
import com.disankyo.security.EncryptException;

/**
 * MD5加密
 * @author xujianxin
 * @time Oct 16, 2013 3:49:00 PM
 */
public class MD5Coder extends AbstractSecurityCoder {

	/**
     * 十六进制表
     */
    private static char[] hexChars = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    
    /**
     * 对给定的数据加密,如果加密的数据为空或null,则抛出IllegalArgumentException
     * @param  source 需要加密的数据
     * @return 加密后的数据
     * @throws EncryptException 加密异常
     * @throws IllegalArgumentException 非法参数异常
     */
	@Override
	public String decrypt(String source) throws DecryptException,
			IllegalArgumentException {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		digest.update(source.getBytes());
		byte[] encryption = digest.digest();
		
		return toHexString(encryption);
	}

	/**
     * MD5加不可逆
     * @param  source 需要解密的数据
     * @return 解密后的数据
     * @throws DecryptException 解密异常
     * @throws IllegalArgumentException 非法参数异常
     */
	@Override
	@Deprecated
	public String encrypt(String source) throws EncryptException,
			IllegalArgumentException {
		return null;
	}
	
	/**
     * 将指定的字节码数组转换成十六进制表示的字符串。
     * @param b 源字节数组。
     * @return 十六进制字符串。
     */
    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChars[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChars[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}
