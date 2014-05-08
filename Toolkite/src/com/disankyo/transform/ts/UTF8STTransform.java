package com.disankyo.transform.ts;

import com.disankyo.transform.CannotTransformException;
import com.disankyo.transform.Transform;
import com.disankyo.util.StringUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 实现UTF8简体转繁体
 * @version 1.00 2012-4-28 10:59:42
 * @since 1.6
 * @author allean
 */
public class UTF8STTransform extends AbstractTransform{

    private RandomAccessFile accessFile;

    private UTF8STTransform(String tableFile) throws FileNotFoundException {
        accessFile = new RandomAccessFile(tableFile, "r");
    }
    
    /**
     * 简体转繁体
     * @param simplified 简体
     * @return 繁体
     * @throws IOException
     * @throws CannotTransformException
     */
    @Override
    public char doTransform(char simplified) throws IOException, CannotTransformException {
        if(!isChinese(simplified)){
            return simplified;
        }

        //文件指针偏移量指向开头
        accessFile.seek(0);

        //如果定位成功,那么前面3个字节即为需要的繁体字.
        if(locate(accessFile, simplified)){
            //当前指针偏移量
            long point = accessFile.getFilePointer();
            accessFile.seek(point - 6);

            accessFile.read(buff);
            return new String(buff, "utf8").charAt(0);
        } else {
            if (flag) {
                return simplified;
            } else {
                throw new CannotTransformException(
                        StringUtil.replaceArgs("Cannot transform[{0}]!", simplified));
            }
        }
    }

    /**
     * 获取该字符在文件的偏移量
     * @param traditional
     * @return
     */
    @Override
    public boolean locate(RandomAccessFile accessFile, char traditional) throws IOException{
        String currentStr = null;

        accessFile.skipBytes(buff.length);
        
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

    public static void main(String[] args) throws Exception {
        Transform<String, String> t = new UTF8STTransform("./build/classes/utf8-ts.tab");
        System.out.println(t.transform("叁龙国际沁￡芯殇"));
    }

}
