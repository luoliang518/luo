package com.luo.login.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JwtUtil {
    //定义token返回头部
    public static String header;

    //token前缀
    public static String tokenPrefix;

    //签名密钥
    public static String secret;

    //有效期
    public static long expireTime;

    /**
     * 创建token
     */
    public static String createToken(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("作者", "罗亮");
        map.put("name", name);
        return tokenPrefix + JWT.create()
                .withHeader(map)
                .withClaim("name",name)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .sign(Algorithm.HMAC512(secret));
    }
}
