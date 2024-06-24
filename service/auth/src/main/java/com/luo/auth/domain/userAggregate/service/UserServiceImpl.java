package com.luo.auth.domain.userAggregate.service;

import com.luo.auth.domain.userAggregate.entity.Tenant;
import com.luo.auth.domain.userAggregate.entity.Token;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.repository.TenantRepository;
import com.luo.auth.domain.userAggregate.repository.UserRepository;
import com.luo.auth.infrastructure.acl.AuthenticationAcl;
import com.luo.auth.infrastructure.converter.TenantConverter;
import com.luo.auth.infrastructure.converter.UserConverter;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import com.luo.auth.infrastructure.util.IPUtil;
import com.luo.auth.infrastructure.util.JwtUtil;
import com.luo.auth.infrastructure.util.RequestUtil;
import com.luo.common.constant.TokenConstant;
import com.luo.common.context.tenant.TenantContext;
import com.luo.common.context.tenant.TenantContextHolder;
import com.luo.common.enums.CacheKeyEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final AuthenticationAcl authenticationAcl;
    private final JwtUtil jwtUtil;
    private final UserConverter userConverter;
    private final TenantConverter tenantConverter;
    private final RedissonClient redisson;

    public void userRegistration(User user) {
        user.checkUserInfo();
        user.encodePassword();
        userRepository.save(userConverter.userToUserPo(user));
    }

    public User authUser(User user, HttpServletRequest request) {
        // 获取用户信息
        user = getUser(user, request);
        // 防止并发生成多个token
        RLock lock = redisson.getLock(CacheKeyEnum.GenerateToken.create(user.getAccount()));
        try {
            if (!lock.tryLock(0,60, TimeUnit.SECONDS)) {
                return user;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 分配token
        String token = jwtUtil.generateToken(user);
        user.setToken(Token.builder().token(token).expires(TokenConstant.TOKEN_SURVIVAL_TIME).build());
        // 构建返回的租户列表
        List<TenantPO> list = tenantRepository.getTenantListByUserId(user.getUserId());
        List<Tenant> tenants = tenantConverter.tenantPOsToTenants(list);
        user.setTenants(tenants);
        // 存入缓存
        redisson.getBucket(CacheKeyEnum.User.create(user.getAccount(), IPUtil.getIPAddress(request),
                        CacheKeyEnum.UserToken.create()))
                .set(token, user.getToken().getExpires(), TimeUnit.SECONDS);
        redisson.getBucket(CacheKeyEnum.User.create(user.getAccount(), IPUtil.getIPAddress(request),
                        CacheKeyEnum.UserInfo.create()))
                .set(user, user.getToken().getExpires(), TimeUnit.SECONDS);
        return user;
    }

    private User getUser(User user, HttpServletRequest request) {
        // 判断请求头是否拥有token
        String tokenHeader = RequestUtil.getTokenHeader(request);
        User oldUser = null;
        if (tokenHeader!=null){
            Jws<Claims> jwt = jwtUtil.tokenAnalysis(tokenHeader);
            // 使用redis优化
            oldUser = (User)redisson.getBucket(CacheKeyEnum.User.create(
                    jwt.getBody().getSubject(), IPUtil.getIPAddress(request),
                    CacheKeyEnum.UserToken.create())
            ).get();
        }
        switch (oldUser){
            case User cacheUser -> user = cacheUser;
            case null -> user = authenticationAcl.authUser(user);
        }
        return user;
    }

    public void choiceTenant(Long tenantId) {
        TenantContextHolder.set(TenantContext.builder()
                .tenantId(tenantId)
//                .tenantName()
                        .build()
        );
    }
}
