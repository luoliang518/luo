package com.luo.login.controller;

import com.luo.common.enums.unifiedEnums.OperateUserEnumError;
import com.luo.common.result.UnifiedServiceHandle;
import com.luo.login.service.UserRoleService;
import com.luo.model.user.dto.UserRoleDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: luoliang
 * @DATE: 2023/1/30/030
 * @TIME: 10:49
 */
@RestController
@RequestMapping("/role")
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;
    @PostMapping("/createRole")
    public UnifiedServiceHandle<OperateUserEnumError> createRole(UserRoleDto userRoleDto){
        userRoleService.createRole(userRoleDto);
        return UnifiedServiceHandle.SUCCESS(OperateUserEnumError.CREATE_ROLE_SUCCESS);
    }
}
