package com.luo.user.controller;

import com.luo.common.enums.unifiedEnums.OperateUserEnum;
import com.luo.common.result.UnifiedServiceHandle;
import com.luo.common.utils.threadLocalUtils.CurrentThreadLocalContext;
import com.luo.user.config.jwt.JwtConfig;
import com.luo.user.service.UserService;
import com.luo.model.user.dto.UserDto;
import com.luo.model.user.vo.UserVo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
    @Resource private JwtConfig jwtConfig;

    @ResponseBody
    @PostMapping("/checkToken")
    public UnifiedServiceHandle<OperateUserEnum> checkToken(ServletRequest request, ServletResponse response){
        // 从本地现场获取用户信息
        UserVo currentThreadLocalContext = CurrentThreadLocalContext.getCurrentThreadLocalContext();
        return UnifiedServiceHandle.getSuccessResult(OperateUserEnum.CHECK_TOKEN_SUCCESS,currentThreadLocalContext);
    }
    @ResponseBody
    @PostMapping("/getToken")
    public UnifiedServiceHandle<OperateUserEnum> getToken(@RequestBody UserVo user){
        userService.checkUser(user.getAccount(),user.getPassword());
        String token = jwtConfig.generateToken(user.getAccount());
        return UnifiedServiceHandle.getSuccessResult(OperateUserEnum.GET_TOKEN_SUCCESS,token);
    }
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
