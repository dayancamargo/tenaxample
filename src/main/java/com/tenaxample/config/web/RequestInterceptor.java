package com.tenaxample.config.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tenaxample.config.datasource.TenantStorage;
import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * An interceptor to add a correlation-id on logback; this will put a [correlation-id] value on all logs;
 */
public class RequestInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object object) {

        MDC.put("correlation", getCorrelation(request));
        setTenant(request);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantStorage.clear();
    }

    private String getCorrelation(final HttpServletRequest request) {
        String id = request.getHeader("correlation-id");
        return  id != null ? id : UUID.randomUUID().toString();
    }

    private void setTenant(final HttpServletRequest request) {

        System.out.println("In preHandle we are Intercepting the Request");
        System.out.println("____________________________________________");
        String requestURI = request.getRequestURI();
        String tenantID = request.getHeader("X-TenantID");
        System.out.println("RequestURI::" + requestURI +" || Search for X-TenantID  :: " + tenantID);
        System.out.println("____________________________________________");

        if (tenantID == null) {
            System.out.println("X-TenantID not present in the Request Header");
            tenantID = "default";
        }
        TenantStorage.setTenantId(tenantID);
    }
}