package com.lscsoft.jfa.commons.util;

import org.apache.commons.lang.StringUtils;

/**
 * 常用的操作工具集锦
 *
 * @author Bond(China)
 * @version 1.0.0
 */
public final class $ {

    private $() {
    }

    /**
     * 判断是否为空
     *
     * @param object 需要被判定的对象
     * @return Boolean
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判断是否为空
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(String object) {
        return StringUtils.isEmpty(object);
    }

    /**
     * 是否相等
     *
     * @param v1 Value1
     * @param v2 Value2
     * @return Boolean
     */
    public static boolean isEqual(Integer v1, Integer v2) {

        if (isNull(v1) || isNull(v2)) {
            return false;
        }
        return v1 == v2;
    }

    /**
     * 是否相等
     *
     * @param v1 Value1
     * @param v2 Value2
     * @return Boolean
     */
    public static boolean isEqual(Long v1, Long v2) {

        if (isNull(v1) || isNull(v2)) {
            return false;
        }
        return v1 == v2;
    }

    /**
     * 是否相等
     *
     * @param v1 Value1
     * @param v2 Value2
     * @return Boolean
     */
    public static boolean isEqual(String v1, String v2) {

        if (isNull(v1) || isNull(v2)) {
            return false;
        }
        return v1.equals(v2);
    }
}
