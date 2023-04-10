package com.luo;

import com.luo.common.utils.springUtils.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: luoliang
 * @DATE: 2023/1/18/018
 * @TIME: 16:34
 */
@SpringBootApplication
//@MapperScan("com.luo.login.mapper")
public class EasyPoiApplication {
    private static Logger logger = LoggerFactory.getLogger(EasyPoiApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(EasyPoiApplication.class, args);
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
