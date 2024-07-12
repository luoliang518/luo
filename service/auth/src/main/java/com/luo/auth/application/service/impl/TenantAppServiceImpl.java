package com.luo.auth.application.service.impl;

import com.luo.auth.application.assembler.UserAssembler;
import com.luo.auth.application.dto.vo.UserVO;
import com.luo.auth.application.service.TenantAppService;
import com.luo.auth.domain.tenantAggregate.service.TenantService;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TenantAppServiceImpl implements TenantAppService {
    private final TenantService tenantService;
    private final UserService userService;
    private final UserAssembler userAssembler;
    @Override
    public UserVO choiceTenant(Long tenantId, HttpServletRequest request) {
        // 更新用户租户信息
        User user = tenantService.choiceTenant(tenantId, request);
        // 更新当前租户中用户权限信息
        return userAssembler.assembleUserVO(userService.initUserRole(user, request));
    }
}
