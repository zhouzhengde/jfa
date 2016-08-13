/*
 * Copyright (c) 2015. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.common;

import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

public abstract class BaseRestController extends BaseController {

    @ExceptionHandler(Exception.class)
    protected Map<String, Object> handleException(Exception ex) {
        return ResultMap.failure(ex);
    }
}
