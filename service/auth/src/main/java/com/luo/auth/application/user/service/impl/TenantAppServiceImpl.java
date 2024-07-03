package com.luo.auth.application.user.service.impl;

import com.luo.auth.application.user.service.TenantAppService;
import com.luo.auth.domain.tenantAggregate.service.TenantService;
import com.luo.auth.domain.userAggregate.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TenantAppServiceImpl implements TenantAppService {
    private final TenantService tenantService;
    @Override
    public User choiceTenant(Long tenantId, HttpServletRequest request) {
        return tenantService.choiceTenant(tenantId,request);
    }
}
