package com.abidat.example.configuration;

public class TenantTracker {

    private static ThreadLocal<Object> tenant = new ThreadLocal<>();


    private static String getDefaultTenant() {
        return "default_db";
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
