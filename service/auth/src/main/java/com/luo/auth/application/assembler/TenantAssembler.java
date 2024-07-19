package com.luo.auth.application.assembler;

import com.luo.auth.application.dto.vo.TenantVo;
import com.luo.auth.domain.tenantAggregate.entity.Tenant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TenantAssembler {
    public List<TenantVo> assembleTenantVo(List<Tenant> tenants) {
        return switch (tenants) {
            case null -> null;
            case List<Tenant> ignored -> tenants.stream().map(tenant -> TenantVo.builder()
                    .tenantId(tenant.getTenantId())
                    .tenantName(tenant.getTenantName())
                    .build()).toList();
        };

    }
}