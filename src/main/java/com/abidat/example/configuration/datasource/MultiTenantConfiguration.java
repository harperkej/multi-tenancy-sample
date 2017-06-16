package com.abidat.example.configuration.datasource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

import static com.abidat.example.configuration.common.Constant.DEFAULT_TENANT;

@Configuration
@ComponentScan(basePackages = {"com.abidat.example"})
public class MultiTenantConfiguration {

    @Autowired
    DataSourceHolder dataSourceHolder;

    //Every time there's an interaction with db, bring all datasources including the ones that are created if any new tenant is created -> that's why the
    //scope of this bean is prototype.
    @Bean
    @Scope(value = "prototype")
    public DataSource createDataSource() throws Exception {
        MultiTenantDataSource multiTenantDataSource = new MultiTenantDataSource();
        try {
            multiTenantDataSource.setDefaultTargetDataSource(dataSourceHolder.getDataSource(DEFAULT_TENANT));
            multiTenantDataSource.setTargetDataSources(dataSourceHolder.getAllDataSources());
            multiTenantDataSource.afterPropertiesSet();
        } catch (Exception e) {
            //TODO This is a temporary exception thrown -> a 'custom' one should be thrown instead :-P.
            throw new Exception(e.getMessage());
        }
        return multiTenantDataSource;
    }

}
