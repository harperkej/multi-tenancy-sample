package com.abidat.example.configuration.datasource;

import com.abidat.example.configuration.TenantTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {


    @Autowired
    DataSourceHolder dataSourceHolder;

    @Override
    protected DataSource determineTargetDataSource() {
        return dataSourceHolder.getDataSource((String) TenantTracker.getCurrentTenant());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantTracker.getCurrentTenant();
    }




}
