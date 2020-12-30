package com.tenaxample.config.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tenaxample.config.tenant.TenantLoader;
import com.tenaxample.config.tenant.TenantStorage;
import com.tenaxample.exception.BaseException;

import lombok.extern.log4j.Log4j2;

/**
 * Set tenant value got from request
 */
@Log4j2
public class TenantInterceptor implements AsyncHandlerInterceptor {
    final TenantLoader tenantLoader;

    public TenantInterceptor(TenantLoader tenantLoader) {
        this.tenantLoader = tenantLoader;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object object) {
        setTenant(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        TenantStorage.clear();
        log.debug("Tenant cleared");
    }

    /**
     * Gets tenant value from request (X-TenantID header) or set default;
     * @param request incoming request
     */
    private void setTenant(final HttpServletRequest request) {
        String tenantID = request.getHeader("X-TenantID");

        if (tenantID == null) {
            log.warn("X-TenantID not present in the Request Header; Set to default");
            tenantID = "default";
        } else {
            log.debug("Calling with tenant {}", tenantID);
            if(!tenantLoader.hasTenant(tenantID)) {
                throw new BaseException("999", "Invalid tenant", "Tenant Error");
            }
        }

        TenantStorage.setTenantId(tenantID);
    }
}
