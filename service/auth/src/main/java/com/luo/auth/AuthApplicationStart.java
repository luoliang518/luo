package com.luo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Description auth服务启动类
 * @Author luoliang
 * @Date 2024/5/21
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AuthApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplicationStart.class,args);
    }
}
