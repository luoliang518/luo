package com.luo.common.context.tenant;

import lombok.Data;

@Data
public class TenantContext {
    private Long tenantId;
    private String tenantName;
}
