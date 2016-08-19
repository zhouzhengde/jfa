/*
 * Copyright (c) 2015. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.util;

/**
 * 获取系统的当前上下文
 *
 * @author Bond(China)
 * @version 1.0.0
 */
public final class EnvironmentUtils {

    private EnvironmentUtils() {
    }

    /**
     * Get current project root context path.
     *
     * @return 系统的当前上下文
     */
    public static String getRootPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

}
