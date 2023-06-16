package com.luo.utils.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luo.utils.util.QRCodeUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class QRCodeController {
    @PostMapping("/qRCode/create")
    public void create(String content, HttpServletResponse servletResponse) {
        try {
            QRCodeUtil.createCodeToOutputStream(content, servletResponse.getOutputStream());
        } catch (Exception ignored) {
        }
    }
}
