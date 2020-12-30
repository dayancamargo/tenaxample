package com.tenaxample.config.tenant;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import lombok.Getter;

@Component
@ConfigurationProperties
@Getter
public class TenantLoader {

    private final Map<String, TenantProperties> tenants = new LinkedHashMap<>();
    private final Map<Object, Object> connectionMap = new LinkedHashMap<>();

    public void setTenants(Map<String, TenantProperties> tenants) {
        tenants.forEach((key, value) -> connectionMap.put(key, new HikariDataSource(value.getDatasource())));
    }

    /**
     * Create all datasource connections based on tenants
     * @return map with tenant - connection
     */
    public Map<Object, Object> getDataSourceConnections() {
        return connectionMap;
    }

    /**
     * Verify if has input tenant
     * @param tenant String with tenant
     * @return
     */
    public Boolean hasTenant(String tenant) {
        return tenants.containsKey(tenant);
    }
}
