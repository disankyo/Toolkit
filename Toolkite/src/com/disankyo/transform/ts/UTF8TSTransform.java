package com.disankyo.transform.ts;

import com.disankyo.transform.CannotTransformException;
import com.disankyo.transform.Transform;
import com.disankyo.util.StringUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 实现UTF8编码繁体转简体的转换器
 * @version 1.00 2012-4-18 17:21:12
 * @since 1.6
 * @author allean
 */
public class UTF8TSTransform extends AbstractTransform{

    private RandomAccessFile accessFile; //码表读取
    
    public UTF8TSTransform(String tableFile) throws FileNotFoundException{
        accessFile = new RandomAccessFile(tableFile, "r");
    }

    /**
     * 繁体转简体
     * @param traditional 繁体字
     * @return 简体
     * @throws IOException
     * @throws CannotTransformException
     */
    @Override
    public char doTransform(char traditional) throws IOException, CannotTransformException{
        if(!isChinese(traditional)){
            return traditional;
        }
        //文件指针偏移量移到文件开头位置
        accessFile.seek(0);

        if(locate(accessFile, traditional)){
            accessFile.read(buff);
            return new String(buff, CODE).charAt(0);
        } else {
            if(flag){
                return traditional;
            } else {
                throw new CannotTransformException(
                        StringUtil.replaceArgs("Cannot transform[{0}]!", traditional));
            }
        }
        
    }

    public static void main(String[] args) throws Exception {
    	long t1 = System.currentTimeMillis();
        Transform<String, String> t = new UTF8TSTransform("E:\\workspace\\Toolkite\\bin\\utf8-ts.tab");
        System.out.println(t.transform("長长江业"));
        long t2 = System.currentTimeMillis();
        System.out.println("========"+(t2 - t1));
    }

}
