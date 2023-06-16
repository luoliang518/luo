package com.luo.utils.util;//package com.luo.utils.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JwtUtil {
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
////    public String createToken1(String name,String role) {
////        Map<String, Object> map = new HashMap<>();
////        map.put("account", name);
////        map.put("role", "罗亮");
////        return tokenPrefix + JWT.create()
////                .withHeader(map)
////                .withClaim("name",name)
////                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
////                .sign(Algorithm.HMAC512(secret));
////    }
//
////    public static byte[] yourSecretKey;
////
////    static {
////        KeyGenerator keyGenerator = null;
////        try {
////            keyGenerator = KeyGenerator.getInstance("HMACSHA256");
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
////        keyGenerator.init(256); // 指定密钥长度
////        SecretKey secretKey = keyGenerator.generateKey();
////        yourSecretKey = secretKey.getEncoded();
////    }
//
    public static String createToken(String name, String role) throws NoSuchAlgorithmException {
        Map<String, Object> map = new HashMap<>();
        map.put("account", name);
        map.put("role", role);
//        SecretKey key = new SecretKeySpec("luo".getBytes(), SignatureAlgorithm.HS256.getJcaName());
//        SecretKey key = new SecretKeySpec(yourSecretKey, SignatureAlgorithm.HS256.getJcaName());

//
//        String token = Jwts.builder()
//                .addClaims(map)
//                .setHeader(map)
//                .setSubject("123")
//                .signWith(key)
//                .compact();
//        return token;
        return "Bearer " + JWT.create()
                .withHeader(map)
                .withClaim("name",name)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600))
                .sign(Algorithm.HMAC512("luo".getBytes()));
    }
//
    public static String verifyToken(String token) throws NoSuchAlgorithmException {
        DecodedJWT decode = JWT.decode(token.substring(7));
        return decode.getClaim("name").asString();
    }
//
//    public static String verifyToken(String token) throws NoSuchAlgorithmException {
//// 解析 JWT token
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey("luo".getBytes()) // 设置密钥，用于验证签名
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        // 从 token 中获取必要的信息
//        String username = claims.getSubject();
//        return username;
//    }
//
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String luoliang = createToken("luoliang", "[\"1\"]");
        System.out.println(luoliang);
        String s = verifyToken(luoliang);
        System.out.println(s);
    }
}
