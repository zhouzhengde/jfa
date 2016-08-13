/*
 * Copyright (c) 2015. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.exception;

public class DataAccessException extends Exception {

    public DataAccessException(String msg, Exception e) {
        super(msg, e);
    }

}
