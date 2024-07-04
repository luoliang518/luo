package com.luo.auth.domain.filter;

import com.luo.auth.domain.userAggregate.entity.Tenant;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.acl.TokenAcl;
import com.luo.auth.infrastructure.util.IPUtil;
import com.luo.auth.infrastructure.util.RequestUtil;
import com.luo.common.context.tenant.TenantContext;
import com.luo.common.context.tenant.TenantContextHolder;
import com.luo.common.enums.CacheKeyEnum;
import com.luo.common.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 租户验证 可选
 */
@Component
@AllArgsConstructor
@Slf4j
@Order(2)
public class TenantFilter extends OncePerRequestFilter {
    private static final Set<String> ALLOWED_PATHS = new HashSet<>(Arrays.asList(
            "/user/**",
            "/tenant/**"
    ));
    private final TokenAcl tokenAcl;
    private final RedissonClient redisson;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {
        // 判断接口是否需要放行
        if(RequestUtil.isPathAllowed(request.getServletPath(),ALLOWED_PATHS)){
            chain.doFilter(request, response);
            return;
        }
        Tenant tenant = initTenant(request);
        if (tenant != null) {
            TenantContextHolder.set(TenantContext.builder()
                    .tenantId(tenant.getTenantId())
                    .tenantName(tenant.getTenantName())
                    .build());
            chain.doFilter(request, response);
        }else {
            // 重定向到租户选择页
        }

    }
    private Tenant initTenant(HttpServletRequest request) {
        // 从请求头 获取token信息
        String tokenHeader = RequestUtil.getTokenHeader(request);
        if (tokenHeader == null) {
            throw new ServiceException("用户信息错误或不存在，请重新登录！");
        }
        Jws<Claims> jwt = tokenAcl.getTokenFromRequest(tokenHeader);
        // 使用redis优化
        User user = (User)redisson.getBucket(CacheKeyEnum.User.create(
                jwt.getBody().getSubject(), IPUtil.getIPAddress(request),
                CacheKeyEnum.UserInfo.create())
        ).get();
        if (user == null) {
            throw new ServiceException("登录信息已过期，请重新登录");
        }
        return user.getCurrentTenant();
    }
}
