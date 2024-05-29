package com.luo.auth.user.interfaces.controller;

import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoliang
 */
@RestController
@RequestMapping("/userAuth")
public class UserAuthController {
    @GetMapping("/say")
    public Response sayHello(){
        return ResponseUtil.success("Hello");
    }
}
