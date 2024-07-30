package com.luo.auth.domain.tenantAggregate.service;

import com.luo.auth.domain.tenantAggregate.entity.Tenant;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.tenantAggregate.respository.TenantRepository;
import com.luo.auth.infrastructure.acl.CacheAcl;
import com.luo.auth.infrastructure.acl.TokenAcl;
import com.luo.auth.infrastructure.converter.TenantConverter;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import com.luo.common.context.tenant.TenantContext;
import com.luo.common.context.tenant.TenantContextHolder;
import com.luo.common.context.user.UserContextHolder;
import com.luo.spring.infrastructure.util.IPUtil;
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
        // 获取用户信息
        User user = cacheAcl.getUserInfo(UserContextHolder.get().getAccount(), IPUtil.getip(request));
        String tenantName = tenantRepository.getById(tenantId).getTenantName();
        user.setCurrentTenant(Tenant.builder()
                .tenantId(tenantId)
                .tenantName(tenantName).build());
        // 分配token
        String token = tokenAcl.generateToken(user);
        user.getToken().setToken(token);
        // 设置本地线程租户信息
        TenantContextHolder.set(TenantContext.builder().tenantId(tenantId).tenantName(tenantName).build());
        return user;
    }

    public User authTenant(User user) {
        User cacheUser = cacheAcl.getUserInfo(user.getAccount(), user.getIp());
        if (cacheUser!=null&&cacheUser.getTenants()!=null&&!cacheUser.getTenants().isEmpty()){
            user.setTenants(cacheUser.getTenants());
        }else {
            // 构建返回的租户列表
            List<TenantPO> list = tenantRepository.getTenantListByUserId(user.getUserId());
            List<Tenant> tenants = tenantConverter.tenantPOsToTenants(list);
            user.setTenants(tenants);
            // 存入缓存
            cacheAcl.saveUserTokenCache(user);
        }
        return user;
    }
}
