package com.disankyo.transform;

/**
 *
 * @version 1.00 2012-4-18 17:18:06
 * @since 1.6
 * @author allean
 */
public interface Transform<T, K> {

    /**
     * 实现原对象转换目标对象
     * @param param 原对象
     * @return 目标对象
     * @throws CannotTransformException
     */
    public T transform(K param) throws CannotTransformException;
}
