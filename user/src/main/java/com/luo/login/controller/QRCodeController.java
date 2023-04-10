package com.luo.login.controller;

import com.luo.login.utils.QRCodeUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/test/qRCode")
public class QRCodeController {
    @PostMapping("/create")
    public void create(String content, HttpServletResponse servletResponse){
        try {
            QRCodeUtil.createCodeToOutputStream(content,servletResponse.getOutputStream());
        }catch (Exception ignored){
        }
    }
}
