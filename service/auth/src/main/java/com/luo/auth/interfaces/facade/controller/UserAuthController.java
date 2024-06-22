package com.luo.auth.interfaces.facade.controller;

import com.luo.auth.application.user.dto.command.UserRegistrationCommand;
import com.luo.auth.application.user.dto.command.VerificationCodeCommand;
import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.application.user.dto.vo.UserCodeVo;
import com.luo.auth.application.user.dto.vo.UserVO;
import com.luo.auth.application.user.service.UserAppService;
import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
/**
 * @author luoliang
 */
@RestController
@AllArgsConstructor
@RequestMapping("/userAuth")
public class UserAuthController {
    private final UserAppService UserAppService;
    /**
     * 项目正常测试接口
     */
    @GetMapping("/say")
    public Response<String> sayHello(){
        return ResponseUtil.success("Hello");
    }
    /**
     * 发送验证码
     */
    @PostMapping("/sendCode")
    public Response<UserCodeVo> sendCode(@RequestBody VerificationCodeCommand verificationCodeCommand){
        return ResponseUtil.success(UserAppService.sendCode(verificationCodeCommand));
    }
    /**
     * 用户注册
     */
    @PostMapping("/userRegistration")
    public Response<?> userRegistration(@RequestBody UserRegistrationCommand userRegistrationCommand){
        UserAppService.userRegistration(userRegistrationCommand);
        return ResponseUtil.success();
    }
    /**
     * 用户账号密码登录
     */
    @PostMapping("/userLogin")
    public Response<UserVO> userLogin(@RequestBody UserQuery userQuery, HttpServletRequest request){
        return ResponseUtil.success(UserAppService.userLogin(userQuery,request));
    }
    /**
     * 用户选择租户
     */
    @PostMapping("/choiceTenant")
    public Response<?> choiceTenant(@RequestBody Long tenantId){
        UserAppService.choiceTenant(tenantId);
        return ResponseUtil.success();
    }
}
