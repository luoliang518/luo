package com.luo.auth.application.user.service;

import com.luo.auth.domain.userAggregate.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface TenantAppService {
    User choiceTenant(Long tenantId, HttpServletRequest request);

}
