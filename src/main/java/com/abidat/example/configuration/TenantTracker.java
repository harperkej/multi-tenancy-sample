package com.abidat.example.configuration;

import com.abidat.example.configuration.common.Constant;

public class TenantTracker {

    private static ThreadLocal<Object> tenant = new ThreadLocal<>();


    private static String getDefaultTenant() {
        return Constant.DEFAULT_TENANT;
    }

    public static void setCurrentTenant(Object object) {
        tenant.set(object);
    }

    public static Object getCurrentTenant() {
        if (tenant.get() == null)
            return getDefaultTenant();
        return tenant.get();
    }

}
