package com.abidat.example.configuration.datasource;


import com.abidat.example.configuration.DataSourceConfigurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MultitenantConfiguration {


    @Autowired
    DataSourceConfigurations dataSourceConfigurations;

    @Bean
    public DataSource createDataSource() throws Exception {
        MultitenantDataSource multitenantDataSource = new MultitenantDataSource();
        try {
            multitenantDataSource.setDefaultTargetDataSource(dataSourceConfigurations.getDefaultDataSource());
            multitenantDataSource.setTargetDataSources(dataSourceConfigurations.getCustomDataSources());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return multitenantDataSource;
    }

}
