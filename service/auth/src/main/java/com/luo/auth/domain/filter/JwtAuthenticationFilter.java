package com.luo.auth.domain.filter;

import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.service.UserServiceImpl;
import com.luo.auth.infrastructure.acl.TokenAcl;
import com.luo.auth.infrastructure.util.IPUtil;
import com.luo.auth.infrastructure.util.RequestUtil;
import com.luo.common.constant.TokenConstant;
import com.luo.common.context.user.UserContext;
import com.luo.common.context.user.UserContextHolder;
import com.luo.common.enums.CacheKeyEnum;
import com.luo.common.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final Set<String> ALLOWED_PATHS = new HashSet<>(Arrays.asList(
            "/userAuth/**"
    ));
    private final TokenAcl tokenAcl;
    private final RedissonClient redisson;
    private final UserServiceImpl userService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {
        // 判断接口是否需要放行
        releaseRequest(request, response, chain);
        // 获取用户信息
        User user = initUser(request);
        // 存入auth信息上下文
        tokenAcl.initAuthentication(request, user);
        // 存入本地线程上下文
        UserContextHolder.set(UserContext.builder()
                .userId(user.getUserId())
                .account(user.getAccount())
                .username(user.getUsername())
                .build());
        // 判断是否需要令牌续命
        if (TokenConstant.TOKEN_REFRESH_TIME > user.getTokenSurvivalTime().getTime()){
            User newUser = userService.authUser(user, request);
            response.setHeader("Authorization", newUser.getToken().getToken());
        }
        chain.doFilter(request, response);
    }

    private User initUser(HttpServletRequest request) {
        // 从请求头 获取token信息
        String tokenHeader = RequestUtil.getTokenHeader(request);
        if (tokenHeader == null) {
            throw new ServiceException("用户信息错误或不存在，请重新登录！");
        }
        Jws<Claims> jwt = tokenAcl.getTokenFromRequest(tokenHeader);
        // 使用redis优化
        User user = (User)redisson.getBucket(CacheKeyEnum.User.create(
                jwt.getBody().getSubject(), IPUtil.getIPAddress(request),
                CacheKeyEnum.UserToken.create())
        ).get();
        if (user == null) {
            throw new ServiceException("登录信息已过期，请重新登录");
        }
        return user;
    }

    private void releaseRequest(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(isPathAllowed(request.getServletPath())){
            chain.doFilter(request, response);
        }
    }

    private boolean isPathAllowed(String requestPath) {
        for (String allowedPath : ALLOWED_PATHS) {
            if (PATH_MATCHER.match(allowedPath, requestPath)) {
                return true;
            }
        }
        return false;
    }
}
