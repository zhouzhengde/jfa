/*
 * Copyright (c) 2015. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.util;

public final class EnvironmentUtils {

    private EnvironmentUtils() {
    }

    /**
     * Get current project root context path.
     *
     * @return
     */
    public static String getRootPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

}
