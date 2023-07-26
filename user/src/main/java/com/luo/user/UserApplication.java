package com.luo.user;

import com.luo.common.utils.springUtils.ApplicationContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: luoliang
 * @DATE: 2023/1/18/018
 * @TIME: 16:34
 */
@SpringBootApplication
@ComponentScan("com.luo")
@MapperScan("com.luo.user.mapper")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class UserApplication {
    private static Logger logger = LoggerFactory.getLogger(UserApplication.class);
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(UserApplication.class, args);
        // 初始化ApplicationContextUtils
        ApplicationContextUtils.setApplication(application);
        Environment env = application.getEnvironment();
        logger.info("\n------------------------------------------------------------\n\t" +
                        "岁岁年年 平平安安 快快乐乐 : \thttp://{}:{}\n" +
                        "-------------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port")
        );
    }
}
