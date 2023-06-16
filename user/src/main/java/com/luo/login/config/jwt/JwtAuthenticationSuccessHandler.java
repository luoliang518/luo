package com.luo.login.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtConfig jwtConfig;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 从认证信息中获取用户名
        String userAccount = authentication.getName();
        // 生成 Token
        String token = jwtConfig.generateToken(userAccount);
        // 将 Token 添加到响应头中
        response.addHeader("Authorization", "Bearer " + token);
        response.sendRedirect("/router/");
    }
}
