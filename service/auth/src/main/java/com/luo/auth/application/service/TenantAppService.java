package com.luo.auth.application.service;

import com.luo.auth.application.dto.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

public interface TenantAppService {
    UserVO choiceTenant(Long tenantId, HttpServletRequest request);

}
