package com.tenaxample.config.datasource;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.TaskExecutor;

import com.tenaxample.config.tenant.TenantLoader;
import com.zaxxer.hikari.HikariDataSource;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.log4j.Log4j2;

/**
 * This class load all data sources from tenants and define default one;
 */
@Log4j2
@Configuration
public class DataSourceConfiguration implements InitializingBean {

    private final ResourceLoader resourceLoader;
    private final TenantLoader tenantLoader;
    private final TaskExecutor taskExecutor;

    @Autowired
    public DataSourceConfiguration(TenantLoader tenantLoader, ResourceLoader resourceLoader, TaskExecutor taskExecutor) {
        this.tenantLoader = tenantLoader;
        this.resourceLoader = resourceLoader;
        this.taskExecutor = taskExecutor;
    }

    @Bean(name = "dataSources")
    @Primary
    public DataSource dataSource() {
        log.debug("Creating dataSource ");
        RoutingDataSource customDataSource = new RoutingDataSource();
        customDataSource.setTargetDataSources(tenantLoader.getDataSourceConnections());
        customDataSource.setDefaultTargetDataSource(tenantLoader.getDataSourceConnections().get("default"));
        return customDataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        runOnAllDataSources();
    }

    private void runOnAllDataSources()  {
        tenantLoader.getDataSourceConnections().forEach((tenant, dataSource) -> {

            log.info("Initializing Liquibase for data source " + tenant);
            final LiquibaseProperties lProperty = tenantLoader.getTenants().get(tenant).getDatasource().getLiquibase();

            SpringLiquibase liquibase;

            if(!Objects.isNull(lProperty)) {
                liquibase = getSpringLiquibase((HikariDataSource) dataSource, lProperty);

                try {
                    log.warn("Starting liquibase for {}", tenant );
                    liquibase.afterPropertiesSet();
                } catch (LiquibaseException e) {
                    log.error("Error on liquibase - " + tenant, e);
                }
            }

            log.info("Liquibase ran for data source " + tenant);
        });
    }

    private SpringLiquibase getSpringLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setContexts(properties.getContexts());
        liquibase.setLabels(properties.getLabels());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setRollbackFile(properties.getRollbackFile());
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setLiquibaseSchema(properties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(properties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(properties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(properties.getDatabaseChangeLogLockTable());
        return liquibase;
    }
}