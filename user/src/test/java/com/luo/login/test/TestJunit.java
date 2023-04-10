package com.luo.login.test;

import com.luo.login.controller.RouterController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureMockMvc
public class TestJunit {
    @Autowired
    private RouterController routerControl;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Test
    public void TestRouterControl() {
        String forObject = restTemplate.getForObject("http://2.0.1.59:518/router/oktaLogin", String.class);
        System.out.println(forObject);
    }
}
