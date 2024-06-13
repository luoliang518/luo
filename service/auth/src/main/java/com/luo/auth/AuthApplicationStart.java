package com.luo.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description auth服务启动类
 * @Author luoliang
 * @Date 2024/5/21
 */
@Slf4j
@SpringBootApplication()
public class AuthApplicationStart {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(AuthApplicationStart.class, args);
        Environment env = application.getEnvironment();
        log.info("\n------------------------------------------------------------\n\t" +
                        "岁岁年年 平平安安 快快乐乐 : \thttp://{}:{}{}\n" +
                        "-------------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path")
        );
    }
}
