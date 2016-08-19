/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.json.annotation;

import java.lang.annotation.*;

/**
 * Use to how to fast access object's attribute
 *
 * @author Bond(China)
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonPathValue {

    enum DataType {
        /**
         * 一般值类型
         */
        Value,
        /**
         * 对象类型
         */
        Object,
        /**
         * 数组类型
         */
        List,
        /**
         * 日期类型
         */
        Date
    }

    /**
     * The path of a JSONObject
     *
     * @return
     */
    String path() default "";

    /**
     * Default Type
     *
     * @return
     */
    DataType dataType() default DataType.Value;

    /**
     * Date format, define for date type.
     *
     * @return
     */
    String pattern() default "yyyy-MM-dd HH:mm:ss";

}
