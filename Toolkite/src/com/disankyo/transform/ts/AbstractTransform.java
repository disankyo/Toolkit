package com.disankyo.transform.ts;

import com.disankyo.transform.CannotTransformException;
import com.disankyo.transform.Transform;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * UTF8简体与繁体相互转换的抽象类
 * @version 1.00 2012-4-28 11:06:07
 * @since 1.6
 * @author allean
 */
public abstract class AbstractTransform implements Transform<String, String>{

    protected static final int EOF = -1;   //读取的结束标志位
    protected byte[] buff = new byte[3];   //每次读取字节的缓冲
    protected boolean flag = true;      //不能转换时,决定是否直接返回
    protected static final String CODE = "utf8";
    
    /**
     * 实际的把UTF8转换方法。
     * @param traditionals 繁体字符（简体字）
     * @return 简体字符（繁体字）
     * @throws CannotTransformException
     */
    public String transform(String param) throws CannotTransformException {
        if(param == null || param.isEmpty()){
            return param;
        } else {
            StringBuilder sb = new StringBuilder();
            for(int index = 0; index < param.length(); index++){
                try {
                    sb.append(doTransform(param.charAt(index)));
                } catch (Exception ex) {
                    throw new CannotTransformException(ex.getMessage(), ex);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 转换
     * @param indexChar
     * @return
     */
    public abstract char doTransform(char indexChar) throws IOException, Exception;

    /**
     * 获取该字符在文件的偏移量
     * @param traditional
     * @return
     */
    public boolean locate(RandomAccessFile accessFile, char traditional) throws IOException{
        String currentStr = null;
        while(accessFile.read(buff) != EOF){
            currentStr = new String(buff, CODE);
            if (currentStr.charAt(0) == traditional) {
                return true;
            } else {
                accessFile.skipBytes(buff.length);
            }
        }

        return false;
    }

    /**
     * 判断字符是否是中文字符
     * @param traditional 字符
     * @return 是中文字符 返回true，否则返回false
     */
    public boolean isChinese(char traditional){
        final int chineseL = 0x4e00;
        final int chineseH = 0x9fa5;

        if(traditional >= chineseL || traditional <= chineseH){
            return true;
        }

        return false;
    }
}
