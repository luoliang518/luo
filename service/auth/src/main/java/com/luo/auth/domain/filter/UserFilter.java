package com.luo.auth.domain.filter;

import com.luo.auth.domain.event.RefreshTokenEvent;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.acl.CacheAcl;
import com.luo.auth.infrastructure.acl.TokenAcl;
import com.luo.common.constant.TokenConstant;
import com.luo.common.context.user.UserContext;
import com.luo.common.context.user.UserContextHolder;
import com.luo.common.exception.ServiceException;
import com.luo.spring.infrastructure.util.AuthorizationUtil;
import com.luo.spring.infrastructure.util.IPUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户验证
 */
@Component
@AllArgsConstructor
@Slf4j
@Order(1)
public class UserFilter extends OncePerRequestFilter {
    private static final Set<String> ALLOWED_PATHS = new HashSet<>(Arrays.asList(
            "/user/**"
    ));
    private final TokenAcl tokenAcl;
    private final CacheAcl cacheAcl;
    private final ApplicationEventPublisher event;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {
        // 判断接口是否需要放行
        if (AuthorizationUtil.isPathAllowed(request.getServletPath(), ALLOWED_PATHS)) {
            chain.doFilter(request, response);
            return;
        }
        // 从请求头 获取token信息
        String tokenHeader = AuthorizationUtil.getTokenHeader(request);
        // 获取ip信息
        String ip = IPUtil.getip(request);
        // 获取用户信息
        User user = initUser(tokenHeader, ip);
        // 存入本地线程上下文
        UserContextHolder.set(UserContext.builder()
                .userId(user.getUserId())
                .account(user.getAccount())
                .username(user.getUsername())
                .build());
        // token信息存入auth信息上下文
        tokenAcl.initAuthentication(request, user);
        // 判断是否需要令牌续命
        if (TokenConstant.TOKEN_REFRESH_TIME > user.getToken().getTokenSurvivalTimeWithNow().getTime()) {
            // 判断是否已经生成token 已生成则直接放入请求头 未生成则发布续命事件生成后放入缓存
            if (user.getToken().getRefreshToken()!=null) {
                response.setHeader("Authorization", "Bearer "+user.getToken().getToken());
            } else {
                event.publishEvent(new RefreshTokenEvent(this, tokenHeader, ip));
                log.info("1111111");
            }
        }
        chain.doFilter(request, response);
    }

    private User initUser(String tokenHeader,String ip) {
        if (tokenHeader == null) {
            throw new ServiceException("用户信息错误或不存在，请重新登录！");
        }
        Jws<Claims> jwt = tokenAcl.getTokenFromRequest(tokenHeader);
        // 使用redis查询
        User user = cacheAcl.getUserInfo(jwt.getBody().getSubject(), ip);
        if (user == null) {
            throw new ServiceException("登录信息已过期，请重新登录");
        }
        return user;
    }
}
