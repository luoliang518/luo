package com.luo.auth.user.interfaces.facade.controller;

import com.luo.auth.user.domain.user.entity.User;
import com.luo.auth.user.infrastructure.converter.UserConverter;
import com.luo.auth.user.infrastructure.repository.mapper.UserMapper;
import com.luo.auth.user.infrastructure.repository.po.UserPO;
import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoliang
 */
@RestController
@RequestMapping("/userAuth")
public class UserAuthController {
    @Autowired
    UserConverter userConverter;
    @GetMapping("/say")
    public Response<String> sayHello(){
        return ResponseUtil.success("Hello");
    }
    @GetMapping("/getUser")
    public Response<User> getUser(){
        UserPO userPO = UserPO.builder()
                .account("123")
                .email("1234")
                .password("234").build();
        System.out.println(userPO.toString());
        User user = userConverter.userPoToUser(userPO);
        return ResponseUtil.success(user);
    }
}
