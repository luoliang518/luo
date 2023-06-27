package com.luo.login.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        // 解析 JWT token
        DecodedJWT decode = JWT.decode(token);
        // 获取用户
        String userAccount = decode.getClaim("userAccount").asString();
        // 获取用户角色
        List<String> roles = decode.getClaim("roles").asList(String.class);
        // 构建用户权限列表
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        // 构建认证对象
        return new UsernamePasswordAuthenticationToken(userAccount, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Ensure the authentication class is of the type we support.
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
