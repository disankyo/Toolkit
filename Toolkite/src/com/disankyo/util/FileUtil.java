package com.disankyo.util;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 對文件操作的常见方法
 * @version 1.00 2010-12-24 15:16:46
 * @since 1.6
 * @author disankyo
 */
public class FileUtil {

    private FileUtil(){
    }
    
    /**
     * 查找当前绝对路径，需要给定一下类型 和一个标记资源。
     * 如果资源处于jar包下，会去掉开头的jar:和结束的文件名。如果处于目录下将去掉文件名。
     * @param clazz 用来查询的类型。
     * @param resourceName 用于定位的资源。
     * @return 资源的绝对路径。
     */
    @SuppressWarnings("rawtypes")
	public static String findLocalPath(Class clazz, String resourceName) {
        String originalPath = clazz.getResource(resourceName).toString();
        return prune(originalPath);
    }

    /**
     * 对给定的字符串进行修剪，去掉开头的jar:如果有的话，去掉结束的文件名称。
     * @param source 原始字符串。
     * @return 修剪后的字符串，如果源为null或者无需修剪将直接原样返回。
     */
    private static String prune(String source) {
        String prefix = "jar:";

        if (source == null || source.isEmpty()) {
            return source;
        }
        StringBuilder buff = new StringBuilder(source);
        int index = 0;
        if (buff.indexOf(prefix) != -1) {
            buff.delete(0, prefix.length());//去除开头的"jar:"字串。
            index = buff.lastIndexOf(".jar");
            buff.delete(index, buff.length());//此时文件名一定是.jar结束，删除从.之后的所有字符。
        }
        buff.delete(0, "file:".length());

        index = buff.lastIndexOf("/");
        buff.delete(index, buff.length());//去掉文件名。
        return buff.toString();
    }
    
    /**
     * 返回指定文件的文件名，或者扩展名
     * @param file
     * @param isExtend
     * @return
     */
    public static String findFileName(File file,boolean isExtend){
        String fileName = file.getName();
        String [] split = fileName.split("\\.");
        if(isExtend){
            return split[1];
        }else{
            return split[0];
        }
    }

    /**
     * 删除目录，不论目录是否为空都将一并删除。
     * 如果参数为File不表示一个目录或者不存在那么将直接返回。
     * @param dir 需要删除的目录。
     */
    public static void deleteDir(File dir){
        if(dir == null || !dir.exists() || !dir.isDirectory()){
            return;
        }
        if(dir.list().length <= 0){
            dir.delete();
        }
        Deque<File> stack = new ArrayDeque<File>();
        stack.push(dir);
        File nowSelect = null;
//        List<File> toStackDirectory = new LinkedList<File>();

        while(true){
            nowSelect = stack.peek();

            for(File nowFile : nowSelect.listFiles()){
                if(nowFile.isFile()){
                    nowFile.delete();
                } else if (nowFile.isDirectory()){
                    deleteDir(nowFile);
//                    toStackDirectory.add(nowFile);
                }
            }
            if(nowSelect.list().length == 0){
                stack.pop();
                nowSelect.delete();
            } else {
//                if(!toStackDirectory.isEmpty()){
//                    for(File directory : toStackDirectory){
//                        stack.add(directory);
//                    }
//                }
            }
        }
    }
}
