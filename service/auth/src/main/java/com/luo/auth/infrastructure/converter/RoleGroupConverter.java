package com.luo.auth.infrastructure.converter;

import com.luo.auth.domain.roleAggregate.entity.RoleGroup;
import com.luo.auth.infrastructure.repository.po.RoleGroupPO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleGroupConverter {
    RoleGroupPO roleGroupToRoleGroupPO(RoleGroup roleGroup);
}
