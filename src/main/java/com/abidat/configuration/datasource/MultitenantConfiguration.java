package com.abidat.configuration.datasource;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Configuration
public class MultitenantConfiguration {


    //TODO The names of configuration files maybe store in db, or any other better solution?
    private List<String> tenantsConfigurationFiles = Arrays.asList("client_1.properties", "client_2.properties");

    private String defaultTenant = "application.properties";

    @Bean
    public DataSource createDataSource() {

        Map<Object, Object> discoveredDataSources = new HashMap<>();

        DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();

        //Get defualt configurations
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(defaultTenant);


            Properties propertiesOfConfigFile = new Properties();
            propertiesOfConfigFile.load(in);

            String tenantIdentifier = propertiesOfConfigFile.getProperty("name");

            defaultDataSource.setDriverClassName(propertiesOfConfigFile.getProperty("spring.datasource.driver-class-name"));
            defaultDataSource.setUrl(propertiesOfConfigFile.getProperty("spring.datasource.url"));
            defaultDataSource.setUsername(propertiesOfConfigFile.getProperty("spring.datasource.username"));
            defaultDataSource.setPassword(propertiesOfConfigFile.getProperty("spring.datasource.password"));


        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get custom configurations!
        for (String tenant : tenantsConfigurationFiles) {
            try {
                InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(tenant);


                Properties propertiesOfConfigFile = new Properties();
                propertiesOfConfigFile.load(in);

                String tenantIdentifier = propertiesOfConfigFile.getProperty("name");

                DriverManagerDataSource dataSource = new DriverManagerDataSource();

                dataSource.setDriverClassName(propertiesOfConfigFile.getProperty("spring.datasource.driver-class-name"));
                dataSource.setUrl(propertiesOfConfigFile.getProperty("spring.datasource.url"));
                dataSource.setUsername(propertiesOfConfigFile.getProperty("spring.datasource.username"));
                dataSource.setPassword(propertiesOfConfigFile.getProperty("spring.datasource.password"));

                discoveredDataSources.put(tenantIdentifier, dataSource);

            } catch (IOException e) {

                System.out.print(e);
                e.printStackTrace();

            }
        }

        MultitenantDataSource multitenantDataSource = new MultitenantDataSource();
        multitenantDataSource.setDefaultTargetDataSource(defaultDataSource);
        multitenantDataSource.setTargetDataSources(discoveredDataSources);

        return multitenantDataSource;
    }

}
