package com.luo.auth.application.service.impl;

import com.luo.auth.application.assembler.UserAssembler;
import com.luo.auth.application.dto.vo.UserVO;
import com.luo.auth.application.service.TenantAppService;
import com.luo.auth.domain.tenantAggregate.service.TenantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TenantAppServiceImpl implements TenantAppService {
    private final TenantService tenantService;
    private final UserAssembler userAssembler;
    @Override
    public UserVO choiceTenant(Long tenantId, HttpServletRequest request) {
        return userAssembler.assembleUserVO(tenantService.choiceTenant(tenantId,request));
    }
}
