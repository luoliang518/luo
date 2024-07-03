package com.luo.auth.interfaces.facade.controller;

import com.luo.auth.application.user.service.TenantAppService;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class TenantController {
    private final TenantAppService tenantAppService;
    /**
     * 用户选择租户
     */
    @PostMapping("/choiceTenant")
    public Response<User> choiceTenant(@RequestBody Long tenantId, HttpServletRequest request){
        return ResponseUtil.success(tenantAppService.choiceTenant(tenantId,request));
    }
}
