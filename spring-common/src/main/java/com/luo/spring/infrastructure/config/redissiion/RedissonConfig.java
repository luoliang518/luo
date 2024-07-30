package com.luo.spring.infrastructure.config.redissiion;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * redisson配置信息
 */
@Data
@Configuration
@ConfigurationProperties("spring.data.redis")
public class RedissonConfig {
    private int database;
    private String host;
    private String password;
    private String port;
    private String timeout = "10s";
    private int connectionPoolSize = 32;
    private int connectionMinimumIdleSize=0;
    private int pingConnectionInterval = 60000;
    private static String ADDRESS_PREFIX = "redis://";

    /**
     * 自动装配
     *
     */
    @Bean
    RedissonClient redissonSingle() {
        Config config = new Config();
        // 使用 Jackson 序列化
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);

        JsonJacksonCodec codec = new JsonJacksonCodec(objectMapper);
        config.setCodec(codec);
        SingleServerConfig serverConfig = config.useSingleServer()
                .setDatabase(database)
                .setAddress(ADDRESS_PREFIX + this.host + ":" + port)
                .setTimeout(Integer.parseInt(this.timeout.replace("s",""))*1000)
                .setPingConnectionInterval(pingConnectionInterval)
                .setConnectionPoolSize(this.connectionPoolSize)
                .setConnectionMinimumIdleSize(this.connectionMinimumIdleSize)
                ;
        if(StringUtils.hasLength(this.password)) {
            serverConfig.setPassword(this.password);
        }
        return Redisson.create(config);
    }
}