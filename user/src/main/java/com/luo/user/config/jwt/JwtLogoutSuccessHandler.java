package com.luo.user.config.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 清除cookie
        clearCookies(request, response);
        // 重定向到首页
        response.sendRedirect("/");
    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 建立一个同名空值的Cookie来覆盖原来的Cookie
                Cookie clearCookie = new Cookie(cookie.getName(), null);
                // 设置Cookie路径
                clearCookie.setPath("/");
                // 立即过期
                clearCookie.setMaxAge(0);
                // 添加到响应中
                response.addCookie(clearCookie);
            }
        }
    }
}
