package com.luo.posting;

import com.luo.common.utils.springUtils.ApplicationContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(exclude = {})
@ComponentScan("com.luo")
@MapperScan("com.luo.posting.mapper")
public class PostingApplication {
    private static Logger logger = LoggerFactory.getLogger(PostingApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(PostingApplication.class, args);
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
