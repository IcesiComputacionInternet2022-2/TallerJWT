package com.icesi.edu.users.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class InitialDataConfig {

    @Autowired
    public void setInitialData(DataSource dataSource, SpringLiquibase springLiquibase) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("sql/data.sql"));
        DatabasePopulatorUtils.execute(resourceDatabasePopulator, dataSource);
    }

}
