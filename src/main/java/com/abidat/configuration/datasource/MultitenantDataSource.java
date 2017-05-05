package com.abidat.configuration.datasource;

import com.abidat.configuration.TenantTracker;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by a.kuci on 4/27/2017.
 */
public class MultitenantDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {

        return TenantTracker.getCurrentTenant();
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

}
