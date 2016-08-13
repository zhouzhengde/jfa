/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.common;

import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

public abstract class BaseController {

    @ExceptionHandler(Exception.class)
    protected Map<String, Object> handleException(Exception ex) {
        return failure("system.error", "系统错误", ex);
    }

    /**
     * 加入结果
     *
     * @param key
     * @param value
     */
    protected void put(String key, Object value) {
        ResultMap.put(key, value);
    }

    /**
     * 加入结果，其KEY为result
     *
     * @param val
     */
    protected void put(Object val) {
        ResultMap.put(val);
    }

    /**
     * 设置成功的结果
     *
     * @return
     */
    protected Map<String, Object> success() {
        return ResultMap.success();
    }

    /**
     * 设置成功的结果
     *
     * @return
     */
    protected Map<String, Object> success(Object value) {
        put(value);
        return ResultMap.success();
    }

    /**
     * 设置成功的结果
     *
     * @return
     */
    protected Map<String, Object> success(String key, Object value) {
        put(key, value);
        return ResultMap.success();
    }

    /**
     * 设置失败的结果
     *
     * @return
     */
    protected Map<String, Object> failure(Exception e) {
        return ResultMap.failure(e);
    }

    /**
     * 设置失败的结果
     *
     * @return
     */
    protected Map<String, Object> failure(String message, Exception e) {
        return ResultMap.failure(message, e);
    }

    /**
     * 设置失败的结果
     *
     * @return
     */
    protected Map<String, Object> failure(String code, String message, Exception e) {
        return ResultMap.failure(code, message, e);
    }

}
