package com.luo.auth.application.user.service;

import com.luo.auth.application.user.dto.vo.UserVO;
import com.luo.auth.domain.userAggregate.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface TenantAppService {
    UserVO choiceTenant(Long tenantId, HttpServletRequest request);

}
