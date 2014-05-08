package com.disankyo.html;

/**
 *
 * @version 1.00 2011-11-1 17:40:59
 * @since 1.6
 * @author allean
 */
public class HtmlUtil {

    private static final char BEGIN = '<';
    private static final char END = '>';
//    private static final char SPACE = ' ';
    private static final char DIVIDE = '/';

    public static String deleteHtmlTags(String source, String[] deleteTags) {
        if (source == null || source.isEmpty()
                || deleteTags == null || deleteTags.length == 0) {
            return source;
        }

        String temp = null;
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char point = source.charAt(i);
            if (point == BEGIN) { //可能的html标签开始符。
                int end = source.indexOf(END, i);
                if (end == -1) { //没有标签结束符。
                    buff.append(source.substring(i));
                } else {
                    temp = source.substring(i + 1, end);
                    if (startsWithDeleteHtmlTag(temp, deleteTags)) {
                        i = end; //以指定的html标签开始或结束的直接跳过。
                    } else {
                        buff.append(point); //虚假的"<>"包含体，追加。
                    }
                }
            } else {
                buff.append(point);
            }
        }

        return buff.toString();
    }

    /*
     * 判断给定的字符串的开头部分是否是指定的要删除的Html标签元素。
     * 如果是，将返回true；不是将返回false。
     */
    private static boolean startsWithDeleteHtmlTag(
            String temp, String[] deleteTags) {
        for (String tag : deleteTags) {
            if (temp.startsWith(tag) || temp.startsWith(DIVIDE + tag)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String source = "<a href=\"url\">经济通</a>";
        String[] deleteTags = new String[]{"a"};
        String result = deleteHtmlTags(source, deleteTags);

        System.out.println(result);
    }
}
