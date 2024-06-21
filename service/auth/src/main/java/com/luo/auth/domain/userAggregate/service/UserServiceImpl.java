package com.luo.auth.domain.userAggregate.service;

import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.domain.userAggregate.acl.AuthenticationAcl;
import com.luo.auth.domain.userAggregate.entity.Tenant;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.repository.TenantRepository;
import com.luo.auth.domain.userAggregate.repository.TenantUserRepository;
import com.luo.auth.domain.userAggregate.repository.UserRepository;
import com.luo.auth.domain.userAggregate.entity.Token;
import com.luo.auth.infrastructure.converter.TenantConverter;
import com.luo.auth.infrastructure.converter.UserConverter;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import com.luo.auth.infrastructure.repository.po.TenantUserPO;
import com.luo.auth.infrastructure.util.JwtUtil;
import com.luo.common.context.tenant.TenantContext;
import com.luo.common.context.tenant.TenantContextHolder;
import com.luo.common.context.user.UserContext;
import com.luo.common.context.user.UserContextHolder;
import com.luo.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final TenantUserRepository tenantUserRepository;
    private final AuthenticationAcl authenticationAcl;
    private final JwtUtil jwtUtil;
    private final UserConverter userConverter;
    private final TenantConverter tenantConverter;

    public void userRegistration(User user) {
        user.checkUserInfo();
        user.encodePassword();
        userRepository.save(userConverter.userToUserPo(user));
    }

    public User authUser(UserQuery userQuery) {
        User userByAccount = userRepository.getUserByAccount(userQuery.getAccount());
        if (userByAccount == null) {
            throw new ServiceException("该账号不存在");
        }
        User user = authenticationAcl.authUser(userQuery);
        String token = jwtUtil.generateToken(user);
        user.setToken(Token.builder().token(token).build());
        // 构建UserContext
        UserContextHolder.set(UserContext.builder()
                .userId(user.getUserId())
                .account(user.getAccount())
                .username(user.getUsername())
                .build());
        // 构建返回的租户列表
        List<TenantPO> list = tenantRepository.getTenantListByUserId(user.getUserId());
        List<Tenant> tenants = tenantConverter.tenantPOsToTenants(list);
        user.setTenants(tenants);
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
