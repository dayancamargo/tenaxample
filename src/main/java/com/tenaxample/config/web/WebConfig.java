package com.tenaxample.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenaxample.config.tenant.TenantLoader;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final TenantLoader tenantLoader;

    @Autowired
    public WebConfig(TenantLoader tenantLoader) {
        this.tenantLoader = tenantLoader;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor());
        registry.addInterceptor(new TenantInterceptor(tenantLoader));
    }
}