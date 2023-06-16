package com.luo.login.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private final AuthenticationManager authenticationManager;

    private List<String> ignoreUrls = new ArrayList<>();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private static final String IGNORE_URL_REGEX =
            ".*((pay/)|(/index)|(/index/.*)" +
                    "|(/login/.*)" +
                    "|(/test/.*)" +
                    "|(/)" +
                    "|(/router/.*)" +
                    "|(/resources/.*)" +
                    "|(/static/.*)" +
                    "|(health)|([.]((html)|(jsp)|(css)|(js)|(gif)|(png)|(ico))))$";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().matches(IGNORE_URL_REGEX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = jwtConfig.extractTokenFromRequest(request);
        if (token != null) {
            try {
                // 在这里解析和验证 JWT token，并构建相应的认证对象
                Authentication authentication = parseAndValidateToken(token);
                // 将认证对象交给 AuthenticationManager 进行认证
                Authentication authenticated = authenticationManager.authenticate(authentication);
                // 将认证对象设置到 SecurityContext 中
                SecurityContextHolder.getContext().setAuthentication(authenticated);
            } catch (Exception e) {
                // 处理验证失败的情况
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }


    private Authentication parseAndValidateToken(String token) {
        // 解析 JWT token
        DecodedJWT decode = JWT.decode(token.substring(7));
        // 获取用户名
        String username = decode.getClaim("name").asString();
        // 获取用户角色
        List<String> roles = decode.getClaim("roles").asList(String.class);
        // 构建用户权限列表
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        // 构建认证对象
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
