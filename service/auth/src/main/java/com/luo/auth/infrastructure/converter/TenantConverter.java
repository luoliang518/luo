package com.luo.auth.infrastructure.converter;

import com.luo.auth.domain.userAggregate.entity.Tenant;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import com.luo.auth.infrastructure.repository.po.UserPO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/21
 */
@Mapper(componentModel = "spring")
public interface TenantConverter {
    List<Tenant> tenantPOsToTenants(List<TenantPO> tenantPOs);
}
