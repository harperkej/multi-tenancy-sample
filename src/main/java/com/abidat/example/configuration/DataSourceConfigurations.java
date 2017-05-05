package com.abidat.example.configuration;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Component
public class DataSourceConfigurations {

    public DataSource getDefaultDataSource() throws Exception {

        DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
        final String defaultTenant = "application.properties";

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
            throw new Exception("Customer configuration error. Check configurations!");
        }
        return defaultDataSource;
    }

    public Map<Object, Object> getCustomDataSources() throws Exception {

        final List<String> tenantsConfigurationFiles = Arrays.asList("client_1.properties", "client_2.properties");

        Map<Object, Object> discoveredDataSources = new HashMap<>();

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

                throw new Exception("Customer configuration error. Check configurations!");
            }
        }
        return discoveredDataSources;
    }

}
