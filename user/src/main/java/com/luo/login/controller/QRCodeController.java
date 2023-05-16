package com.luo.login.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luo.login.utils.QRCodeUtil;
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
    @PostMapping("/listTest")
    public void listTest(@RequestBody Map<String ,Object> request) {
        String templateId =request.get("templateId").toString();
        Object contentsControl = request.get("contentsControl");
        Object o = JSONArray.toJSON(contentsControl);
        JSONArray jsonArray =  JSONObject.parseArray(o.toString());
        List<Map<String,String>> strings = new ArrayList<>();
        for (Object o1 : jsonArray) {
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            JSONObject jsonObject = JSONObject.parseObject(o1.toString());
            String contentId = jsonObject.getString("contentId");
            String contentValue = jsonObject.getString("contentValue");
            stringStringHashMap.put("contentId","123");
            stringStringHashMap.put("contentValue","123");
            strings.add(stringStringHashMap);
        }
        System.out.println(strings);
    }

//    @PostMapping("/eec")
//    public UserDto eec(@RequestBody UserDto userDto) {
////        List<EECComponent> extObject = EECContextAccessor.getContext().getExtObject();
////        if (!extObject.isEmpty()) {
////            EECComponent eecComponent = extObject.get(0);
////            System.out.println(eecComponent.getExtField());
////        }
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("123", "123");
//        ((EECComponent) userDto).setExtField(map);
//
//
//        List<EECComponent> extObject1 = EECContextAccessor.getContext().getExtObject();
//        for (EECComponent o : extObject1) {
//            System.out.println(o.toString());
//            System.out.println(o.getExtField().get("123"));
//        }
//        return userDto;
//    }

    @PostMapping("/qRCode/create")
    public void create(String content, HttpServletResponse servletResponse) {
        try {
            QRCodeUtil.createCodeToOutputStream(content, servletResponse.getOutputStream());
        } catch (Exception ignored) {
        }
    }
}
