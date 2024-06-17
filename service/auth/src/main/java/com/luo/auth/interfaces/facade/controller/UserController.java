package com.luo.auth.interfaces.facade.controller;

import com.luo.auth.application.user.dto.command.RoleGroupCommand;
import com.luo.auth.application.user.service.UserAppService;
import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserAppService userAppService;
    @GetMapping("/say")
    public Response<String> sayHello(){
        return ResponseUtil.success("Hello");
    }
    @PostMapping("/addOrEditRoleGroup")
    public Response<?> addOrEditRoleGroup(@RequestBody RoleGroupCommand roleGroupCommand){
        userAppService.addOrEditRoleGroup(roleGroupCommand);
        return ResponseUtil.success();
    }
}
