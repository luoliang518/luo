package com.luo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: luoliang
 * @DATE: 2023/1/18/018
 * @TIME: 16:34
 */
@Controller
@RequestMapping("/router")
public class RouterController {

    @RequestMapping({"/","/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "views/login";
    }
    @ResponseBody
    @PostMapping("/logout")
    public String logout(){
        return "success";
    }

    @ResponseBody
    @RequestMapping("/loginSuccess")
    public String loginSuccess() {
        return "success";
    }

    /**
     * OKTA回调地址
     * @return
     */
    @ResponseBody
    @RequestMapping("/oktaCallback")
    public String oktaCallback(HttpServletRequest requestMap) {

        return "success";
    }

}
