package com.tenaxample.config.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tenaxample.config.datasource.TenantStorage;

import lombok.extern.log4j.Log4j2;

/**
 * An interceptor to add a correlation-id on logback; this will put a [correlation-id] value on all logs;
 */
@Log4j2
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
        String requestURI = request.getRequestURI();
        String tenantID = request.getHeader("X-TenantID");

        if (tenantID == null) {
            log.warn("X-TenantID not present in the Request Header; Set to default");
            tenantID = "default";
        } else {
            log.debug("Calling with tenant {}", tenantID);
        }
        TenantStorage.setTenantId(tenantID);
    }
}