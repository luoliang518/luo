package com.luo.auth.domain.tenantAggregate.service;

import com.luo.auth.domain.userAggregate.entity.Tenant;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.tenantAggregate.respository.TenantRepository;
import com.luo.auth.infrastructure.acl.CacheAcl;
import com.luo.auth.infrastructure.acl.TokenAcl;
import com.luo.auth.infrastructure.converter.TenantConverter;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import com.luo.common.context.user.UserContext;
import com.luo.common.context.user.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;
    private final TenantConverter tenantConverter;
    private final TokenAcl tokenAcl;
    private final CacheAcl cacheAcl;
    public User choiceTenant(Long tenantId, HttpServletRequest request) {
        UserContext userContext = UserContextHolder.get();
        User user = cacheAcl.getUserInfo(userContext.getAccount(),request);
        String tenantName = tenantRepository.getById(tenantId).getTenantName();
        user.setCurrentTenant(Tenant.builder()
                .tenantId(tenantId)
                .tenantName(tenantName).build());
        // 分配token
        String token = tokenAcl.generateToken(user);
        user.setDefSurvivalToken(token);
        // 存入缓存
        cacheAcl.saveUserTokenCache(request, user);
        return user;
    }

    public User authTenant(User user, HttpServletRequest request) {
        User cacheUser = cacheAcl.getUserInfo(user.getAccount(), request);
        if (cacheUser!=null&&cacheUser.getTenants()!=null&&!cacheUser.getTenants().isEmpty()){
            user.setTenants(cacheUser.getTenants());
        }else {
            // 构建返回的租户列表
            List<TenantPO> list = tenantRepository.getTenantListByUserId(user.getUserId());
            List<Tenant> tenants = tenantConverter.tenantPOsToTenants(list);
            user.setTenants(tenants);
            // 存入缓存
            cacheAcl.saveUserTokenCache(request, user);
        }
        return user;
    }
}
