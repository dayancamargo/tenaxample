package com.tenaxample.config.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import lombok.extern.log4j.Log4j2;

/**
 * An interceptor class to get values for correalation id  (same as sleuth lib)
 */
@Log4j2
public class RequestInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object object) {

        setCorrelation(request);
        return true;
    }

    /**
     * Gets correlation value or create one if absent
     * @param request incoming request
     */
    private void setCorrelation(final HttpServletRequest request) {
        String id = request.getHeader("correlation-id");
        MDC.put("correlation", id != null ? id : UUID.randomUUID().toString());
    }
}