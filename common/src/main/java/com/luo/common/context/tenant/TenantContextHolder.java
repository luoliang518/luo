package com.luo.common.context.tenant;

public class TenantContextHolder {
    private static ThreadLocal<TenantContext> threadLocal = new ThreadLocal<>();
    public static TenantContext get() {
        return threadLocal.get();
    }
    public static void set(TenantContext tenantContext) {
        threadLocal.set(tenantContext);
    }
}
