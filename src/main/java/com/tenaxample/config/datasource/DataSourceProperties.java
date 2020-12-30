package com.tenaxample.config.datasource;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * <p>This class will load data sources definitions from application.yml.</p>
 * <p>Each definition in "tenants.datasource" will be loaded into map and converted to a hikari data source instance</p>
 */
@Component
@ConfigurationProperties(prefix = "tenants") // define wich property data sources will be
@RefreshScope
public class DataSourceProperties {

    // Load data sources property from path defined in @ConfigurationProperties
    private Map<Object, Object> datasources = new LinkedHashMap<>();

    public Map<Object, Object> getDatasources() {
        return datasources;
    }

    /**
     * Get a map with datasource and its configs converting to Datasource instances
     * @param dataSources map with key (instance name) and its params
     */
    public void setDataSources(Map<String, Map<String, String>> dataSources) {
        dataSources.forEach((key, value) -> this.datasources.put(key, convertToDataSource(value)));
    }

    /**
     * Convert all properties to datasource instance
     * @param source Map with parameters to create a datasource instance
     * @return a new datasource instance
     */
    private DataSource convertToDataSource(Map<String, String> source) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(source.get("url"));
        config.setDriverClassName(source.get("driver-class-name"));
        config.setUsername(source.get("username"));
        config.setPassword(source.get("password"));
        config.setMaximumPoolSize(Integer.parseInt(source.get("maximum-pool-size")));
        config.setPoolName(source.get("pool-name"));

        return new HikariDataSource(config);
    }
}