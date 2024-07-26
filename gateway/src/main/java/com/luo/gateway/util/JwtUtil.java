package com.luo.gateway.util;

import com.luo.common.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    private PublicKey publicKey;
    @PostConstruct
    public void init() {
        try {
            // 读取公钥文件
            InputStream publicKeyStream = getClass().getClassLoader().getResourceAsStream("pk/luo_public_key.pem");
            if (publicKeyStream == null) {
                throw new ServiceException("未找到公钥文件");
            }
            String publicKeyPEM = new String(publicKeyStream.readAllBytes(), StandardCharsets.UTF_8);
            publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            // 将公钥转换为 PublicKey 对象
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new ServiceException("读取密钥库/公钥出错",e);
        }
    }

    public Jws<Claims> tokenAnalysis(String token) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
    }
}
