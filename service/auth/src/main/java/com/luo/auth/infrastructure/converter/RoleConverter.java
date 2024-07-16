package com.luo.auth.infrastructure.converter;


import com.luo.auth.domain.roleAggregate.entity.Role;
import com.luo.auth.infrastructure.repository.po.RolePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author luoliang
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {
    @Mapping(source = "id", target = "roleId")
    Role rolePOToRole(RolePO rolePO);
    List<Role> rolePOSToRoles(List<RolePO> rolePOS);
}
