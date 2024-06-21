package com.luo.auth.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luo.auth.domain.roleAggregate.repository.RoleGroupRepository;
import com.luo.auth.domain.userAggregate.repository.TenantRepository;
import com.luo.auth.infrastructure.repository.mapper.RoleGroupMapper;
import com.luo.auth.infrastructure.repository.mapper.TenantMapper;
import com.luo.auth.infrastructure.repository.mapper.TenantUserMapper;
import com.luo.auth.infrastructure.repository.po.RoleGroupPO;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import com.luo.auth.infrastructure.repository.po.TenantUserPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TenantRepositoryImpl extends ServiceImpl<TenantMapper, TenantPO> implements TenantRepository {
    private TenantUserMapper tenantUserMapper;
    @Override
    public List<TenantPO> getTenantListByUserId(Long userId) {
        List<TenantUserPO> tenantUserPOS = tenantUserMapper.selectList(Wrappers.<TenantUserPO>lambdaQuery().eq(TenantUserPO::getUserId, userId));
        return list(lambdaQuery().in(TenantPO::getId, tenantUserPOS.stream().map(TenantUserPO::getTenantId)));
    }
}
