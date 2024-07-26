package com.luo.gateway;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/7/24
 */
@Slf4j
@SpringBootApplication
public class GatewayApplicationStart {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(GatewayApplicationStart.class, args);
        Environment env = application.getEnvironment();
        log.info("\n------------------------------------------------------------\n\t" +
                        "岁岁年年 平平安安 快快乐乐 : \thttp://{}:{}\n" +
                        "-------------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port")
        );
    }
    @Bean
    KeyResolver userOrIpKeyResolver() {
        return exchange -> {
            String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
            if (userId != null) {
                return Mono.just(userId);
            }
            String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostString();
            return Mono.justOrEmpty(ip);
        };
    }
}
