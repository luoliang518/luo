package com.luo.common.context.tenant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenantContext {
    private Long tenantId;
    private String tenantName;
}
