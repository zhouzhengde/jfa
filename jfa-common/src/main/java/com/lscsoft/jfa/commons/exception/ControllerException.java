/*
 * Copyright (c) 2015. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.exception;

public class ControllerException extends Exception {

    public ControllerException(String msg, Exception e) {
        super(msg, e);
    }

    public ControllerException(Exception e){
        super(e);
    }
}
