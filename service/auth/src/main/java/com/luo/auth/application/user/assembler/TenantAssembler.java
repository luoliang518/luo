package com.luo.auth.application.user.assembler;

import com.luo.auth.application.user.dto.vo.TenantVo;
import com.luo.auth.domain.userAggregate.entity.Tenant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TenantAssembler {
    public List<TenantVo> assembleTenantVo(List<Tenant> tenants) {
        return tenants.stream().map(tenant -> TenantVo.builder()
                .tenantId(tenant.getTenantId())
                .tenantName(tenant.getTenantName())
                .build()).toList();
    }
}
