package com.luo.auth.interfaces.facade.controller;

import com.luo.auth.application.dto.command.UserRegistrationCommand;
import com.luo.auth.application.dto.command.VerificationCodeCommand;
import com.luo.auth.application.dto.query.UserQuery;
import com.luo.auth.application.dto.vo.UserCodeVo;
import com.luo.auth.application.dto.vo.UserVO;
import com.luo.auth.application.service.UserAppService;
import com.luo.common.result.Response;
import com.luo.common.result.ResultCode;
import com.luo.common.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
/**
 * @author luoliang
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
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
        return ResponseUtil.success(ResultCode.SEND_CODE_SUCCESS,UserAppService.sendCode(verificationCodeCommand));
    }
    /**
     * 用户注册
     */
    @PostMapping("/userRegistration")
    public Response<?> userRegistration(@RequestBody UserRegistrationCommand userRegistrationCommand){
        UserAppService.userRegistration(userRegistrationCommand);
        return ResponseUtil.success(ResultCode.USER_REGISTER_SUCCESS);
    }
    /**
     * 用户账号密码登录
     */
    @PostMapping("/userLogin")
    public Response<UserVO> userLogin(@RequestBody UserQuery userQuery, HttpServletRequest request){
        return ResponseUtil.success(UserAppService.userLogin(userQuery,request));
    }

}
