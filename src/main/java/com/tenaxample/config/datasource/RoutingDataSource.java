package com.tenaxample.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.tenaxample.config.tenant.TenantStorage;

/**
 * This will select witch datasource use based on tenant sent on request
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return TenantStorage.getTenantId();
    }
}
