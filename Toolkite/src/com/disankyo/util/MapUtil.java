package com.disankyo.util;

import java.util.Collections;
import java.util.Map;

/**
 * 对Map操作常见的方法
 * @version 1.00 2010-12-25 22:56:26
 * @since 1.6
 * @author disankyo
 */
@SuppressWarnings("rawtypes")
public class MapUtil {

    private MapUtil(){
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
     * 自定义加载因为来计算出Map的容器大小。
     * @param number 条目数量。条目数量会取绝对值。
     * @param loadFactor 加载因子。如果小于0或者大于1，将以默认的0.75F为加载因子。
     * @return map的容量大小。
     */
    public static int calculateMapInitialCapacity(final int number,float loadFactor){
        int _number = Math.abs(number);
        float _loadFactor = 0.75F;
        if(loadFactor > 0.0F || loadFactor <= 1.0F){
            _loadFactor = loadFactor;
        }
        int capacity = (int) (_number / _loadFactor);

        return capacity;
    }

    /**
     * 以0.75作为加载因子来计算出Map需要的容器大小。
     * @param number 条目数量。条目数量会取绝对值。
     * @return map的容量大小。
     */
    public static int calculateMapInitialCapacity(final int number){
        return calculateMapInitialCapacity(number, 0.0F);
    }
}
