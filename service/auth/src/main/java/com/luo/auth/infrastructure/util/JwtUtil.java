package com.luo.auth.infrastructure.util;

import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.config.security.JasyptConfig;
import com.luo.common.exception.ServiceException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@Data
@Slf4j
public class JwtUtil {
    @Value("${auth.author}")
    private String author;
    @Value("${auth.password}")
    private String pass;

    private PrivateKey privateKey;
    private PublicKey publicKey;
    @PostConstruct
    public void init() {
        try {
            // Load keystore
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream inputStream = new ClassPathResource("pk/luo_keystore.p12").getInputStream();
            keyStore.load(inputStream, pass.toCharArray());
            // 从秘钥库生成私钥
            privateKey = (PrivateKey) keyStore.getKey(author, pass.toCharArray());
            // 从秘钥库生成公钥
//            Certificate cert = keyStore.getCertificate(author);
//            publicKey = cert.getPublicKey();
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
            log.error("读取密钥库/公钥出错",e);
        }
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getAccount())
                .claim("userId",user.getUserId())
                .claim("userName",user.getUsername())
                .claim("account",user.getAccount())
                .claim("email",user.getEmail())
                .claim("phone",user.getPhone())
                .setIssuedAt(new Date())
                .setExpiration(user.getTokenDueTime())
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
        return true;
    }
}
