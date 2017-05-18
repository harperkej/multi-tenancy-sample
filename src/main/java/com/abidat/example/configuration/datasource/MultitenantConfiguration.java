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
public class MultitenantConfiguration {


    @Autowired
    DataSourceHolder dataSourceHolder;

    @Bean
    @Scope(value = "prototype")
    public DataSource createDataSource() throws Exception {
        MultitenantDataSource multitenantDataSource = new MultitenantDataSource();
        try {
            multitenantDataSource.setDefaultTargetDataSource(dataSourceHolder.getDataSource(DEFAULT_TENANT));
            multitenantDataSource.setTargetDataSources(dataSourceHolder.getAllDataSources());
            multitenantDataSource.afterPropertiesSet();
        } catch (Exception e) {
            //TODO This is a temporary exception thrown -> a 'custom' one should be thrown instead :-P.
            throw new Exception(e.getMessage());
        }
        return multitenantDataSource;
    }

}
