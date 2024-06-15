package com.luo.auth.interfaces.facade.controller;

import com.luo.auth.application.user.dto.command.UserRegistrationCommand;
import com.luo.auth.application.user.dto.vo.UserCodeVo;
import com.luo.auth.infrastructure.converter.UserConverter;
import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.application.user.dto.vo.UserVO;
import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.luo.auth.application.user.service.UserAppService;
/**
 * @author luoliang
 */
@RestController
@AllArgsConstructor
@RequestMapping("/userAuth")
public class UserAuthController {
    private final UserAppService UserAppService;
    @GetMapping("/say")
    public Response<String> sayHello(){
        return ResponseUtil.success("Hello");
    }
    /**
     * 发送验证码
     */
    @PostMapping("/sendCode")
    public Response<UserCodeVo> sendCode(@RequestBody UserRegistrationCommand userRegistrationCommand){
        return ResponseUtil.success(UserAppService.sendCode(userRegistrationCommand));
    }
    /**
     * 用户注册
     */
    @PostMapping("/userRegistration")
    public Response<?> userRegistration(@RequestBody UserRegistrationCommand userRegistrationCommand){
//        UserAppService.userRegistration(userRegistrationCommand);
        return ResponseUtil.success();
    }
    /**
     * 用户登录
     */
    @PostMapping("/userLogin")
    public Response<UserVO> userLogin(@RequestBody UserQuery userQuery){
        return ResponseUtil.success(UserAppService.userLogin(userQuery));
    }
}
