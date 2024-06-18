package com.luo.auth.interfaces.facade.controller;

import com.luo.auth.application.user.dto.command.RoleGroupCommand;
import com.luo.auth.application.user.service.RoleAppService;
import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final RoleAppService roleAppService;
    @PostMapping("/addOrEditRoleGroup")
    public Response<?> addOrEditRoleGroup(@RequestBody RoleGroupCommand roleGroupCommand){
        roleAppService.addOrEditRoleGroup(roleGroupCommand);
        return ResponseUtil.success();
    }
}
