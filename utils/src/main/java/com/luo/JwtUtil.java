//package com.luo;
//
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@ConfigurationProperties(prefix = "jwt")
//@Component
//@Data
//public class JwtUtil {
//    //定义token返回头部
//    public String header;
//
//    //token前缀
//    public String tokenPrefix;
//
//    //签名密钥
//    public String secret;
//
//    //有效期
//    public long expireTime;
//
//    /**
//     * 创建token
//     */
//    public String createToken(String name,String role) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("account", name);
//        map.put("role", "罗亮");
//        return tokenPrefix + JWT.create()
//                .withHeader(map)
//                .withClaim("name",name)
//                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
//                .sign(Algorithm.HMAC512(secret));
//    }
//}
