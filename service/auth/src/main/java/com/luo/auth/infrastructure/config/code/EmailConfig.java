package com.luo.auth.infrastructure.config.code;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
public class EmailConfig {
    @Value("${spring.mail.username}")
    private String from;
}
