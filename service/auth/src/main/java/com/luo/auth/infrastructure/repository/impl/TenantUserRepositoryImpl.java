package com.luo.auth.infrastructure.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luo.auth.domain.roleAggregate.repository.RoleGroupRepository;
import com.luo.auth.domain.userAggregate.repository.TenantUserRepository;
import com.luo.auth.infrastructure.repository.mapper.RoleGroupMapper;
import com.luo.auth.infrastructure.repository.mapper.TenantUserMapper;
import com.luo.auth.infrastructure.repository.po.RoleGroupPO;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import com.luo.auth.infrastructure.repository.po.TenantUserPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TenantUserRepositoryImpl extends ServiceImpl<TenantUserMapper, TenantUserPO> implements TenantUserRepository {

}
