package com.disankyo.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * 
 * @author xjx
 *
 */
public class MD5Util {
	
    /*** 16进制字符集 */
    private static final char HEX_DIGITS[] = 
    	{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /*** 指定算法为MD5的MessageDigest */
    private static MessageDigest messageDigest = null;

    /*** 初始化messageDigest的加密算法为MD5 */
    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件的MD5值
     * @param file 目标文件
     * @return MD5字符串
     */
    public static String getFileMD5String(File file) {
        String ret = "";
        FileInputStream inputStream = null;
        FileChannel channel = null;
        try {
        	inputStream = new FileInputStream(file);
            channel = inputStream.getChannel();
            ByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messageDigest.update(byteBuffer);
            ret = bytesToHex(messageDigest.digest());
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                	inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (channel != null) {
                try {
                	channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    /**
     * 获取文件的MD5值
     * @param fileName 目标文件的完整名称
     * @return MD5字符串
     */
    public static String getFileMD5String(String fileName) {
        return getFileMD5String(new File(fileName));
    }

    /**
     * MD5加密字符串
     * @param str 目标字符串
     * @return MD5加密后的字符串
     */
    public static String getMD5String(String str) {
        return getMD5String(str.getBytes());
    }

    /**
     * MD5加密以byte数组表示的字符串
     * @param bytes 目标byte数组
     * @return MD5加密后的字符串
     */
    public static String getMD5String(byte[] bytes) {
        messageDigest.update(bytes);
        return bytesToHex(messageDigest.digest());
    }

    /**
     * 校验密码与其MD5是否一致
     * @param pwd 密码字符串
     * @param md5 基准MD5值
     * @return 检验结果
     */
    public static boolean checkPassword(String pwd, String md5) {
        return getMD5String(pwd).equalsIgnoreCase(md5);
    }

    /**
     * 校验密码与其MD5是否一致
     * @param pwd 以字符数组表示的密码
     * @param md5 基准MD5值
     * @return 检验结果
     */
    public static boolean checkPassword(char[] pwd, String md5) {
        return checkPassword(new String(pwd), md5);
    }

    /**
     * 检验文件的MD5值
     * @param file 目标文件
     * @param md5 基准MD5值
     * @return 检验结果
     */
    public static boolean checkFileMD5(File file, String md5) {
        return getFileMD5String(file).equalsIgnoreCase(md5);
    }

    /**
     * 检验文件的MD5值
     * @param fileName 目标文件的完整名称
     * @param md5 基准MD5值
     * @return 检验结果
     */
    public static boolean checkFileMD5(String fileName, String md5) {
        return checkFileMD5(new File(fileName), md5);
    }

    /**
     * 将字节数组转换成16进制字符串
     * @param bytes 目标字节数组
     * @return 转换结果
     */
    public static String bytesToHex(byte bytes[]) {
    	return toHexString(bytes);
    }

    /**
     * 将指定的字节码数组转换成十六进制表示的字符串。
     * @param b 源字节数组。
     * @return 十六进制字符串。
     */
    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();
//        String md5 = getFileMD5String(new File("E:\\SmartFoxServer_2X\\SFS2X\\script\\Servers\\LastName1.json"));
        //0959a406cf314303bef33d0057f8ca27
//        System.out.println(getFileMD5String(new File("E:\\SmartFoxServer_2X\\SFS2X\\script\\Servers\\LastName.json")));
        //0959A406CF314303BEF33D0057F8CA27
        String md5 = getFileMD5String(new File("E:\\SmartFoxServer_2X\\SFS2X\\script\\Servers\\LastName.json"));
        
        long end = System.currentTimeMillis();
        System.out.println("MD5:\t" + md5 + "\nTime:\t" + (end - begin) + "ms");
        
        
        System.out.println(new BASE64Encoder().encode("abcdefghijklmnopqrstuvwx".getBytes()));
        System.out.println(getMD5String("abcdefghijklmnopqrstuvwxyz"));
    }
}
