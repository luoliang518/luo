package com.luo.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/*
Redis本身提供了一下一种序列化的方式：
GenericToStringSerializer: 可以将任何对象泛化为字符串并序列化
Jackson2JsonRedisSerializer: 跟JacksonJsonRedisSerializer实际上是一样的
JacksonJsonRedisSerializer: 序列化object对象为json字符串
JdkSerializationRedisSerializer: 序列化java对象
StringRedisSerializer: 简单的字符串序列化
如果我们存储的是String类型，
默认使用的是StringRedisSerializer 这种序列化方式。
如果我们存储的是对象，默认使用的是 JdkSerializationRedisSerializer，
也就是Jdk的序列化方式（通过ObjectOutputStream和ObjectInputStream实现，缺点是我们无法直观看到存储的对象内容）。
 */
@Configuration(proxyBeanMethods = false)
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String pwd;
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 泛型改成 String Object，方便我们的使用
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Json序列化配置
        // 使用 json解析对象
        Jackson2JsonRedisSerializer objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 通过 ObjectMapper进行转义
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectJackson2JsonRedisSerializer.setObjectMapper(om);
        // String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用 String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的 key也采用 String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // Value的序列化方式采用 jackSon
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        // hash的 value序列化也采用 jackSon
        template.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(pwd);
        redisStandaloneConfiguration.setPort(port);
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jpcb.poolConfig(jedisPoolConfig);
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean(name = "jedisPoolConfig")
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWait(Duration.ofMillis(1000));
        return jedisPoolConfig;
    }
}
