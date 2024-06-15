package com.luo.auth.infrastructure.util;

import com.luo.auth.domain.userAggregate.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Date;

@Component
@Data
public class JwtUtil {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void init() {
        try {
            // Load keystore
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream inputStream = new ClassPathResource("pk/luo_keystore.p12").getInputStream();
            keyStore.load(inputStream, "luoliang".toCharArray());

            // Extract private key
//            privateKey = (PrivateKey) keyStore.getKey("luoliang", "2000518".toCharArray());

            // Extract public key
            Certificate cert = keyStore.getCertificate("luoliang");
            publicKey = cert.getPublicKey();

//            System.out.println("Private Key: " + privateKey);
//            System.out.println("Public Key: " + publicKey);

        } catch (Exception e) {
            // Log the error
            System.err.println("Error initializing JwtUtil: " + e.getMessage());
            e.printStackTrace();
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
                .setExpiration(user.getToken().getExpires())
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
        return true;
    }
}
