package com.tenaxample.config.datasource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * This class load all data sources from tenants and define default one;
 */
@Configuration
public class DataSourceConfiguration {

    private final DataSourceProperties dataSourceProperties;

    public DataSourceConfiguration(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Primary
    @Bean
    @RefreshScope
    public DataSource dataSource() {
        RoutingDataSource customDataSource = new RoutingDataSource();
        customDataSource.setTargetDataSources(dataSourceProperties.getDatasources());
        customDataSource.setDefaultTargetDataSource(dataSourceProperties.getDatasources().get("default"));
        return customDataSource;
    }


    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

}