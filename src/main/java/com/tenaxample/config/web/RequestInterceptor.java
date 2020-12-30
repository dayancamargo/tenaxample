package com.tenaxample.config.web;

import com.tenaxample.config.datasource.TenantStorage;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * An interceptor class to get values for correalation id  (same as sleuth lib) and set tenant value (for datasource selection)
 */
@Log4j2
public class RequestInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object object) {

        setCorrelation(request);
        setTenant(request);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantStorage.clear();
    }

    /**
     * Gets correlation value or create one if absent
     * @param request incoming request
     */
    private void setCorrelation(final HttpServletRequest request) {
        String id = request.getHeader("correlation-id");
        MDC.put("correlation", id != null ? id : UUID.randomUUID().toString());
    }

    /**
     * Gets tenant value from request (X-TenantID header) or set default;
     * @param request incoming request
     */
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