package com.tenaxample.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TenantDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantStorage.getTenantId();
    }
}
