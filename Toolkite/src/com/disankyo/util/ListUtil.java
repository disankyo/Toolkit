package com.disankyo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 对列表操作常见的方法
 * @version 1.00 2010-12-24 15:21:25
 * @since 1.6
 * @author disankyo
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ListUtil {

    private ListUtil(){
    }

    /**
     * 用于包装空列表或者传入的列表值为null，返回Collections.EMPTY_LIST。
     * 否则原样返回。
     * @param list 需要检查的列表。
     * @return 如果为空或者值为null，都将返回Collections.EMPTY_LIST，否则原样返回。
     */
	public static List packEmptyCollection(List list) {
        if (list == null || list.isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {
            return list;
        }
    }

    /**
     * 用于包装空列表或者传入的列表值为null，返回Collections.EMPTY_LIST。
     * 否则原样返回。
     * @param set 需要检查的列表。
     * @return 如果为空或者值为null，都将返回Collections.EMPTY_LIST，否则原样返回。
     */
    public static Set packEmptyCollection(Set set) {
        if (set == null || set.isEmpty()) {
            return Collections.EMPTY_SET;
        } else {
            return set;
        }
    }

    /**
     * 用于包装空列表或者传入的列表值为null，返回Collections.EMPTY_LIST。
     * 否则原样返回。
     * @param map 需要检查的列表。
     * @return 如果为空或者值为null，都将返回Collections.EMPTY_LIST，否则原样返回。
     */
    public static Map packEmptyCollection(Map map) {
        if (map == null || map.isEmpty()) {
            return Collections.EMPTY_MAP;
        } else {
            return map;
        }
    }

    /**
     * 删除列表中重复的元素(无序)
     * @param list 要操作的源列表
     * @return 删除重复元素后的列表
     */
    public static List deleteRepeatElement(List list) {
        if (list == null || list.isEmpty()) {
            return packEmptyCollection(list);
        }

        Set set = new HashSet(list);
        set.clear();
        list.addAll(set);

        return list;
    }

    /**
     * 删除列表中重复的元素(有序)
     * @param list 要操作的源列表
     * @return 删除重复元素后的列表
     */
    public static List deleteRepeatElementWithOrder(List list) {
        if(list == null || list.isEmpty()){
            return packEmptyCollection(list);
        }
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

}
