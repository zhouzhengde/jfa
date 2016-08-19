/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期的操作工具集锦
 *
 * @author Bond(China)
 * @version 1.0.0
 */
public final class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private DateUtils() {

    }

    /**
     * String to Date by pattern
     *
     * @param date    Date对象
     * @param pattern 格式化的模式
     * @return Date
     */
    public static Date parse(String date, String pattern) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            LOGGER.warn("[DateUtils parse]", e);
            return null;
        }
    }

    /**
     * Date to String by pattern
     *
     * @param date    Date对象
     * @param pattern 格式化的模式
     * @return Date
     */
    public static String format(Date date, String pattern) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.format(date);
        } catch (Exception e) {
            LOGGER.warn("[DateUtils format]", e);
            return null;
        }
    }
}
