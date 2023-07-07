package com.luo.login.config.jwt;

import com.luo.common.utils.threadLocalUtils.CurrentThreadLocalContext;
import com.luo.model.user.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtConfig jwtConfig;
    private AuthenticationManager authenticationManager;

    private static final String IGNORE_URL_REGEX =
            ".*((pay/)|(/index)|(/index/.*)" +
                    "|(/login/.*)" +
                    "|(/test/.*)" +
//                    "|(/)" +
                    "|(/user/createUser)" +
                    "|(/user/getToken)" +
                    "|(/router/.*)" +
                    "|(/resources/.*)" +
                    "|(/static/.*)" +
                    "|(health)|([.]((html)|(jsp)|(css)|(js)|(gif)|(png)|(ico))))$";

    public JwtAuthenticationFilter(JwtConfig jwtConfig, AuthenticationManager authenticationManager) {
        this.jwtConfig = jwtConfig;
        this.authenticationManager = authenticationManager;
    }

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
                Authentication authentication = new UsernamePasswordAuthenticationToken(token, null);
                // 将认证对象交给 AuthenticationManager 进行认证
                Authentication authenticated = authenticationManager.authenticate(authentication);
                // 将认证对象设置到 SecurityContext 中
                SecurityContextHolder.getContext().setAuthentication(authenticated);
                // 将用户信息存入本地线程
                List<String> roles =  authenticated.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                CurrentThreadLocalContext.USER_THREAD_LOCAL_CONTEXT.set(new UserVo(authenticated.getPrincipal().toString(),roles,null));
            } catch (Exception e) {
                log.info("验证失败",e);
                // 处理验证失败的情况
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
