package com.luo.auth.infrastructure.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luo.auth.domain.roleAggregate.repository.RoleRepository;
import com.luo.auth.infrastructure.repository.mapper.RoleMapper;
import com.luo.auth.infrastructure.repository.po.RolePO;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends ServiceImpl<RoleMapper, RolePO> implements RoleRepository {
}
