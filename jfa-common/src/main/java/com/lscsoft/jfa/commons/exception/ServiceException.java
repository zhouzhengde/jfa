/*
 * Copyright (c) 2015. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.exception;

public class ServiceException extends Exception {

    public ServiceException(String msg, Exception e) {
        super(msg, e);
    }
}
