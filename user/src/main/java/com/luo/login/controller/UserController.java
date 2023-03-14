package com.luo.login.controller;

import com.luo.common.enums.unifiedEnums.OperateUserEnum;
import com.luo.common.result.UnifiedServiceHandle;
import com.luo.login.service.UserService;
import com.luo.model.user.UserDto;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 15:09
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @ResponseBody
    @PostMapping("/createUser")
    public UnifiedServiceHandle<OperateUserEnum> createUser(@RequestBody UserDto user){
        userService.createUser(user);
        return UnifiedServiceHandle.SUCCESS(OperateUserEnum.CREATE_USER_SUCCESS);
    }
    @Secured({"ROLE_admin"})
    @RequestMapping("/admin/{id}")
    public String admin(@PathVariable("id") int id) {
        return "views/admin/" + id;
    }

    @Secured({"ROLE_user"})
    @RequestMapping("/{id}")
    public String user(@PathVariable("id") int id) {
        return "views/user/" + id;
    }
}
