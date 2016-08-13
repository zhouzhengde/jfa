/*
 * Copyright (c) 2015. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GlobalInterceptor extends HandlerInterceptorAdapter {

    private ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    protected static final Logger LOGGER = LoggerFactory.getLogger(ResultMap.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime.set(System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.info( "The Request:[" + request.getRequestURI() + "] Cost Time:" + (System.currentTimeMillis() - startTime.get()) + "(ms)");
        super.afterCompletion(request, response, handler, ex);
    }
}
