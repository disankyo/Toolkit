package com.disankyo.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串的一些操作
 * @version 1.00 2010-12-24 15:14:33
 * @since 1.6
 * @author disankyo
 */
public class StringUtil {

    private StringUtil(){
    }
    
    /**
     * 获得字符串的长度，半角字符和符号计1，其余计2
     * @param string 原始字符串
     * @return 字符串的数量
     */
    public static int getWordCount(String string) {
        int count = 0;
        if (string == null || string.isEmpty()) {
        } else {
            char point;
            for (int i = 0; i < string.length(); i++) {
                point = string.charAt(i);
                if (point >= 0 && point < 127) {
                    count += 1;
                } else {
                    count += 2;
                }
            }
        }
        return count;
    }

    /**
     * 半角转全角
     * @param source 源字符串
     * @return 全角字符串
     */
    public static String toSBC(String source) {
        String fullString = "";

        if (source == null || source.isEmpty()) {
            return source;
        }

        char[] ch = source.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == ' ') {
                ch[i] = '\u3000';
            } else if (ch[i] < '\177') {
                ch[i] = (char) (ch[i] + 65248);
            }
        }
        fullString = new String(ch);
        return fullString;
    }

    /**
     * 全角转半角
     * @param source 源字符串
     * @return
     */
    public static String toDBC(String source) {
        String halfString = "";

        if (source == null || source.isEmpty()) {
            return source;
        }

        char[] ch = source.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '\u3000') {
                ch[i] = ' ';
            } else if (ch[i] < '\uFF5F' && ch[i] > '\uFF00') {
                ch[i] = (char) (ch[i] - 65248);
            }
        }
        halfString = new String(ch);
        return halfString;
    }

    /**
     * 判断字符串是否是阿拉伯数字
     * @param source 源字符串
     * @return
     */
    public boolean isDigit(String source) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(source).matches();
    }

    /**
     * 判断字符串是否是字母
     * @param source
     * @return
     */
    public boolean isPhonetic(String source) {
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        return pattern.matcher(source).matches();
    }

    /**
     * 检查文本中是否包含指定关键字，如果返回值不为null即表示返回值是第一个出现在文本
     * 中的关键字。被检查的文本或者关键字列表为null或者为空都将返回null.
     * @param text 被检查的文本。
     * @param pattern 要检查的关键字
     * @return 如果所有关键字都没有出现在文本中即返回null，否则返回出现的关键字字串。
     */
    public static String contains(String text, Pattern pattern) {
        if (text == null || text.isEmpty() || pattern == null) {
            return null;
        }
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    /**
     * 将原字符串中由{}表示的参数占位符用param参数中的值去替换。
     * 参数占位符从{0}开始：第一个参数为{0}，第二个参数为{1}，...
     * @param source 源字符串。
     * @param params 需要替换的参数列表。
     * @return 替换后的字符串。
     */
    public static String replaceArgs(String source, Object... params) {
        if (source == null || source.isEmpty()
                || params == null || params.length == 0) {
        	
            return source;
        }

        StringBuilder result = new StringBuilder(source);
        StringBuilder temp = new StringBuilder();
        String param = null;
        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < params.length; i++) {
            if(params[i] == null){
                param = null;
            } else {
                param = params[i].toString();
            }

            temp.delete(0, temp.length());
            temp.append("{");
            temp.append(i);
            temp.append("}");
            
            while (true) {
                startIndex = result.indexOf(temp.toString(), endIndex);
                if (startIndex == -1) {
                    break;
                }
                endIndex = startIndex + temp.length();
                result.replace(startIndex, endIndex, param == null ? "" : param);
            }

            startIndex = 0;
            endIndex = 0;
        }
        
        return result.toString();
    }

    /**
     * 将原字符串中由{}包含并在给定参数params映射中出现的字符串替换成params中的值。
     * 如果原始字符串中含有占位符，但是参数映射里的值却是null或者没有此映射都将不会被替换。
     * 例如:
     * Map<String, String> params = new HashMap<String, String>();
     * params.put("name","db");
     * String result = replaceArgs("This is test {name}.",params);
     * System.out.println(result);
     * 最终的打印结果是"This is test db.".
     * 如果多处出现{name}那么都将被替换成"db"字串。
     *
     * @param source 原始字符串。
     * @param params 参数列表。
     * @return 已经替换过值的字符串。如果原始字符串或者参数为空都将返回原始字符串。
     */
    public static String replaceArgs(String source, Map<String, String> params) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        if (params == null || params.isEmpty()) {
            return null;
        }
        char startAlert = '{';
        char endAlert = '}';
        char point;
        StringBuilder result = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        String value = null;
        String param = null;
        Boolean alert = false;
        for (int i = 0; i < source.length(); i++) {
            point = source.charAt(i);
            if (point == startAlert) {
                alert = true;
            } else if (point == endAlert){
                alert = false;

                param = temp.toString();
                temp.delete(0, temp.length());
                value = params.get(param);
                if (value == null){
                    temp.append(startAlert);
                    temp.append(param);
                    temp.append(endAlert);
                }
                result.append(value == null ? temp.toString() : value);
                temp.delete(0, temp.length());
            } else {
                if (alert) {
                    temp.append(point);
                } else {
                    result.append(point);
                }
            }
        }
        return source.toString();
    }

    public static void main(String[] args){
        System.out.println("================"+replaceArgs("ewrwe {0}.", "rreee"));
    }
    
    
    /**
	 * 将字符串首字母转化为小写
	 * @version 2012-7-19 下午04:53:47
	 * @param source
	 * @return
	 */
	public static String firstLetterToLowerCase(String source){
		return Character.toLowerCase(source.charAt(0)) + source.substring(1);
	}
}
