package com.abidat.example.configuration.datasource;

import com.abidat.example.configuration.common.Constant;
import com.abidat.example.schema.SchemaGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.abidat.example.configuration.common.Constant.*;

/**
 * This is a singleton bean which has a 'local cache' used to store data sources for all clients aka tenants.
 */
@Component
@Scope(value = "singleton")
public class DataSourceHolder {

    private ConcurrentMap<String, DataSource> dataSources;

    @PostConstruct
    public void init() throws Exception {
        try {

            String s = "total_useless_string -> used for debug";

            //create the default schema at startup - if it does not exist!
            SchemaGenerator.createDefaultSchemaAtStartup();

            dataSources = new ConcurrentHashMap<>();

            //at the startup of the application, put the default datasource in our 'cache'.
            dataSources.put(DEFAULT_TENANT, this.getDefaultDataSource());


            Properties properties = this.getPropertiesFromApplicationPropertiesFile();

            String mySqlDriver = properties.getProperty(Constant.DRIVER_CLASS_NAME_KEY);
            String urlOfDefaultDb = properties.getProperty(Constant.URL_OF_DATABASE_KEY) + Constant.DEFAULT_TENANT;
            String user = properties.getProperty(Constant.USER_OF_DB_KEY);
            String pass = properties.getProperty(Constant.PASS_OF_DB_KEY);


            Connection connection = DriverManager.getConnection(urlOfDefaultDb, user, pass);

            String query = "SELECT c.company_number FROM company_entity c";

            Statement statement = connection.createStatement();

            ResultSet res = statement.executeQuery(query);

            while (res.next()) {
                String companyName = res.getString("company_number");
                if (companyName != null)
                    dataSources.put(companyName, this.createNewDataSource(companyName));
            }

            statement.close();
            connection.close();


        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }


    public DataSource getDataSource(String key) {
        return dataSources.get(key);
    }

    public DataSource createNewDataSource(String key) throws Exception {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);

        Properties propertiesOfConfigFile = new Properties();
        propertiesOfConfigFile.load(in);

        dataSource.setDriverClassName(propertiesOfConfigFile.getProperty(DRIVER_CLASS_NAME_KEY));
        dataSource.setUrl(propertiesOfConfigFile.getProperty(URL_OF_DATABASE_KEY) + key);
        dataSource.setUsername(propertiesOfConfigFile.getProperty(USER_OF_DB_KEY));
        dataSource.setPassword(propertiesOfConfigFile.getProperty(PASS_OF_DB_KEY));
        dataSources.put(key, dataSource);
        return dataSource;
    }


    private DataSource getDefaultDataSource() throws Exception {
        DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);

        Properties propertiesOfConfigFile = new Properties();
        propertiesOfConfigFile.load(in);

        defaultDataSource.setDriverClassName(propertiesOfConfigFile.getProperty(DRIVER_CLASS_NAME_KEY));
        defaultDataSource.setUrl(propertiesOfConfigFile.getProperty(URL_OF_DATABASE_KEY) + DEFAULT_TENANT);
        defaultDataSource.setUsername(propertiesOfConfigFile.getProperty(USER_OF_DB_KEY));
        defaultDataSource.setPassword(propertiesOfConfigFile.getProperty(PASS_OF_DB_KEY));

        return defaultDataSource;
    }

    private Properties getPropertiesFromApplicationPropertiesFile() throws Exception {

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);

        Properties propertiesOfConfigFile = new Properties();
        propertiesOfConfigFile.load(in);

        return propertiesOfConfigFile;
    }


    public Map<Object, Object> getAllDataSources() {

        Map<Object, Object> result = new HashMap<>();

        this.dataSources.forEach((k, v) -> {
            result.put(k, v);
        });

        return result;
    }


}
