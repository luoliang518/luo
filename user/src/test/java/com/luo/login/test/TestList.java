package com.luo.login.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: luoliang
 * @DATE: 2023/2/3/003
 * @TIME: 16:58
 */
public class TestList {
    public static void main(String[] args) {
//        SimpleDateFormat a = new SimpleDateFormat("yyyyMMdd");
//        Date date = new Date();
//        String b=a.format(date);
//        System.out.println(date);
//        System.out.println(b);
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
        System.out.println(yyyyMMdd);
    }
    public static String createToken(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("作者", "罗亮");
        map.put("name", name);
        return JWT.create()
                .withHeader(map)
                .withClaim("name",name)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000))
                .sign(Algorithm.HMAC512("jkhfghcv1nbxxdx"));
    }
}
