package com.luo.auth.infrastructure.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luo.auth.domain.roleAggregate.repository.RoleGroupRepository;
import com.luo.auth.infrastructure.repository.mapper.RoleGroupMapper;
import com.luo.auth.infrastructure.repository.po.RoleGroupPO;
import org.springframework.stereotype.Repository;

@Repository
public class RoleGroupRepositoryImpl extends ServiceImpl<RoleGroupMapper, RoleGroupPO> implements RoleGroupRepository {
}
