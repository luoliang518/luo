package com.luo.auth.interfaces.facade.controller;

import com.luo.auth.infrastructure.user.converter.UserConverter;
import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.application.user.dto.vo.UserVO;
import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luo.auth.application.user.service.UserAppService;
/**
 * @author luoliang
 */
@RestController
@AllArgsConstructor
@RequestMapping("/userAuth")
public class UserAuthController {
    private final UserAppService UserAppService;
    private final UserConverter userConverter;
    @GetMapping("/say")
    public Response<String> sayHello(){
        return ResponseUtil.success("Hello");
    }
    @GetMapping("/userLogin")
    public Response<UserVO> userLogin(@RequestBody UserQuery userQuery){
        return ResponseUtil.success(UserAppService.userLogin(userQuery));
    }
}
