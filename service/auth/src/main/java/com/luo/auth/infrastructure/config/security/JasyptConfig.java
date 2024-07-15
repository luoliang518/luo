package com.luo.auth.infrastructure.config.security;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {
    @Value("${auth.password}")
    private String password;
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        // 加密密钥
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        return encryptor;
    }
}
