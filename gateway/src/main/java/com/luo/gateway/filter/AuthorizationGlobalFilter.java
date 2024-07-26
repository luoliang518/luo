package com.luo.gateway.filter;

import com.luo.common.constant.TokenConstant;
import com.luo.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 授权全局过滤器
 * @作者: 罗亮
 * @创建时间: 2022-07-03 16:25
 **/
@AllArgsConstructor
@Component
public class AuthorizationGlobalFilter implements GlobalFilter, Ordered {
    private final JwtUtil jwtUtil;
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final Set<String> ALLOWED_PATHS = Set.of(
            "/auth/**"
            // 添加其他需要放行的路径
    );
    private static final String TOKEN = "token";
    private static final HttpStatus UNAUTHORIZED_STATUS = HttpStatus.NETWORK_AUTHENTICATION_REQUIRED;
    private static final ConcurrentHashMap<String, String> tokenCache = new ConcurrentHashMap<>();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (isPathAllowed(request.getPath().toString())) {
            return chain.filter(exchange);
        }

        String token = getTokenFromRequest(request);

        if (StringUtils.hasText(token)) {
            // 合法性校验
            String parsedToken = tokenCache.computeIfAbsent(token, this::parseToken);
            // 存入redis todo
            if (StringUtils.hasText(parsedToken)){
                request.mutate().header(TokenConstant.AUTH, TokenConstant.BEARER_PREFIX + token);
                return chain.filter(exchange);
            }
        }
        response.setStatusCode(UNAUTHORIZED_STATUS);
        return response.setComplete();
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        String token = request.getQueryParams().getFirst(TokenConstant.AUTH);
        if (token != null && !token.isEmpty()) {
            return token;
        }

        token = request.getHeaders().getFirst(TokenConstant.AUTH);
        if (token != null && !token.isEmpty()) {
            return token;
        }

        token = getTokenFromCookies(request.getCookies(), TokenConstant.AUTH);
        if (token != null && !token.isEmpty()) {
            return token;
        }

        token = request.getQueryParams().getFirst(TOKEN);
        if (token != null && !token.isEmpty()) {
            return token;
        }

        token = request.getHeaders().getFirst(TOKEN);
        if (token != null && !token.isEmpty()) {
            return token;
        }

        return getTokenFromCookies(request.getCookies(), TOKEN);
    }
    private String parseToken(String token) {
        try {
            // 解析Token
            Jws<Claims> jws = jwtUtil.tokenAnalysis(token);
            Claims body = jws.getBody();
            // 校验Token的时间合法性
            if (!isTokenValid(body)) {
                return null;
            }
            // 如果Token合法，返回解析后的结果
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isTokenValid(Claims claims) {
        Date now = new Date();
        Date expiration = claims.getExpiration();
        Date notBefore = claims.getNotBefore();
        Date issuedAt = claims.getIssuedAt();

        if (expiration != null && expiration.before(now)) {
            // Token已过期
            return false;
        }

        if (notBefore != null && notBefore.after(now)) {
            // Token还未生效
            return false;
        }

        if (issuedAt != null && issuedAt.after(now)) {
            // Token签发时间在当前时间之后（不应出现这种情况）
            return false;
        }
        // Token合法
        return true;
    }
    private String getTokenFromCookies(MultiValueMap<String, HttpCookie> cookies, String cookieName) {
        if (cookies != null && !cookies.isEmpty()) {
            HttpCookie cookie = cookies.getFirst(cookieName);
            if (cookie != null) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private boolean isPathAllowed(String requestPath) {
        return ALLOWED_PATHS.stream().anyMatch(allowedPath -> PATH_MATCHER.match(allowedPath, requestPath));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
