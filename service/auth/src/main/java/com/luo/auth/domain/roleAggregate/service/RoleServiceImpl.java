package com.luo.auth.domain.roleAggregate.service;

import com.luo.auth.domain.roleAggregate.entity.RoleGroup;
import com.luo.auth.domain.roleAggregate.repository.RoleGroupRepository;
import com.luo.auth.infrastructure.converter.RoleGroupConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl {
    private final RoleGroupRepository roleGroupRepository;
    private final RoleGroupConverter roleGroupConverter;
    public void addOrEditRoleGroup(RoleGroup roleGroup) {
        // 校验是否已存在同名角色组
        roleGroupRepository.saveOrUpdate(roleGroupConverter.roleGroupToRoleGroupPO(roleGroup));
    }
}
