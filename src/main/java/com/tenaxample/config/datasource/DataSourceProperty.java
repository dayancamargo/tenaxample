package com.tenaxample.config.datasource;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

import com.zaxxer.hikari.HikariConfig;

import lombok.Getter;
import lombok.Setter;

/**
 * THis class is used to load data sources and liquibase properties from application file
 */
@Getter @Setter
public class DataSourceProperty extends HikariConfig {
    private LiquibaseProperties liquibase;
}