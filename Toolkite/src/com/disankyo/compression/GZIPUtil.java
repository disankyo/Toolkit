package com.disankyo.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.disankyo.log.LoggerFactory;
import com.disankyo.log.SyncLogUtil;

/**
 * 使用GZIP算法实现压缩和解压缩
 * @version 1.00 2010-12-24 14:26:21
 * @since 1.6
 * @author disankyo
 */
public final class GZIPUtil {

    private static final SyncLogUtil LOG = LoggerFactory.getLog(GZIPUtil.class);

    private GZIPUtil(){
    }
    
    /**
     * 压缩
     * @param data 要压缩的源数据
     * @return 压缩后的数据
     */
    public static byte[] getZip(final byte[] data) throws IOException{
        final ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = null;

        try {
            gzipOut = new GZIPOutputStream(byteOutput);
            gzipOut.write(data);
        } finally {
            if (gzipOut != null) {
                gzipOut.close();
            }
        }
        
        return byteOutput.toByteArray();
    }

    /**
     * 解压缩
     * @param gzipData 要解压缩的gZip数据
     * @return 解压缩后的数据
     */
    public static byte[] deZip(final byte[] gzipData) throws IOException{
        final ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        GZIPInputStream gzipIn = null;
        try {
            gzipIn = new GZIPInputStream(new ByteArrayInputStream(gzipData));
            final byte[] buf = new byte[1024];
            int length = gzipIn.read();
            while(length != -1){
                byteOutput.write(buf, 0, length);
                
                length = gzipIn.read();
            }
        } catch (IOException ex) {
        	LOG.errorLog(ex);
        } finally {
            if(gzipIn != null){
                gzipIn.close();
            }
        }
        
        return byteOutput.toByteArray();
    }
}
