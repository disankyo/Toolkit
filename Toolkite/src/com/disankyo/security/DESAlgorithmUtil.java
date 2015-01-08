package com.disankyo.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 数据加密解密3DES的ECB算法
 * @author xjx
 *
 */
public class DESAlgorithmUtil {

	//key为abcdefghijklmnopqrstuvwx的Base64编码   与客户端约定的公钥
	public static final String PUBLIC_KEY = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4";
		
	public static void main(String[] args) throws Exception {
		
		long t1 = System.nanoTime();
		System.out.println(encryptECBSource("YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4"));
		long t2 = System.nanoTime();
		System.out.println(t2 - t1);
		
		//从加密后的字符串先解码 再解密
		System.out.println(decodeECBFromSource("VyuQAQupxtJ36RL+gZSTg74RqOckFF5lPO6Km2Jmw5QVQI1v4VgFow=="));
		long t3 = System.nanoTime();
		System.out.println(t3 - t2);
	}

	/**
	 * 把源字符串加密
	 * @param source 需要加密的源字符串
	 * @return
	 */
	public static String encryptECBSource(String source) throws Exception{
		byte[] key = new BASE64Decoder().decodeBuffer(PUBLIC_KEY);
		byte[] sourceBytes = source.getBytes("UTF-8");
		byte[] encryptBytes = des3EncodeECB(key, sourceBytes);
		String encrypt =new BASE64Encoder().encode(encryptBytes); 
		
		return encrypt;
	}
	
	/**
	 * 从加密后的字符串先解码 再解密
	 * @param encrypt 加密后的字符串
	 * @return
	 */
	public static String decodeECBFromSource(String encrypt) throws Exception{
		//先解码公钥
		byte[] key = new BASE64Decoder().decodeBuffer(PUBLIC_KEY);
		byte[] encryptBytes = new BASE64Decoder().decodeBuffer(encrypt);
		byte[] decodeBytes = ees3DecodeECB(key, encryptBytes);
		
		return new String(decodeBytes, "UTF-8");
	}
	
	/**
	 * ECB加密,不要IV
	 * @param key 密钥
	 * @param data 明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeECB(byte[] key, byte[] data)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * ECB解密,不要IV
	 * @param key 密钥
	 * @param data Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] ees3DecodeECB(byte[] key, byte[] data)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}
	
	
	/**
	 * CBC加密
	 * @param key 密钥
	 * @param keyiv IV
	 * @param data 明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * CBC解密
	 * 
	 * @param key 密钥
	 * @param keyiv IV
	 * @param data Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}
	
}
