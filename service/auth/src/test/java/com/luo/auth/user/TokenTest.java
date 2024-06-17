package com.luo.auth.user;

import com.luo.auth.infrastructure.util.JwtUtil;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenTest {
    @Autowired
    private JwtUtil jwtUtil;
    @Test
    public void test() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJsdW9saWFuZyIsInVzZXJOYW1lIjoi572X5LquIiwiYWNjb3VudCI6Imx1b2xpYW5nIiwiZW1haWwiOiIxODIyMzc1OTg0QHFxLmNvbSIsInBob25lIjoiMTczNTcxNzA5NDIiLCJpYXQiOjE3MTg2MDQxNTYsImV4cCI6MTcxODYwNzc1Nn0.G8Zcn4ZgUaZZfGrv-b-tQHaBnIq0nl98EaxGG_8Lw4jw1SaYhr0kENau8uWDQLVgY_mGSCKE-XtK88uPG3EOkNq1WrG9u4ckUGkG3ZmN0qkb_KZRcr97yiaVgTCIr7SAafnD4244pAzaQN7I81EBCnphBj97jsSzfJHaRNVdmK8yl_pZNVnmRWawYdNExq5B94B6sHYtcXO1o4McN6eKSLiPP9Z4HeFFjehpAFxK1o4p_JildKHG_t9_1wojg3Vkw3e8HbaIyR-MxwmL_QuGs-d3WZMgyWsqOziwegBE4ZdXE8hEtASjSdwaCzQygkSWzAGB1mgOz10DQyEfFLkZyw";
        boolean b = jwtUtil.validateToken(token);
        System.out.println(b);
    }
}
