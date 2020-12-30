package com.tenaxample.config.tenant;

import com.tenaxample.config.datasource.DataSourceProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TenantProperties {

    private String publicName;
    private DataSourceProperty datasource;
}
