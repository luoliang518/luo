package com.luo.auth.infrastructure.converter;


import com.luo.auth.domain.roleAggregate.entity.Permission;
import com.luo.auth.domain.roleAggregate.entity.Role;
import com.luo.auth.domain.userAggregate.entity.Tenant;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.repository.po.PermissionPO;
import com.luo.auth.infrastructure.repository.po.RolePO;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import com.luo.auth.infrastructure.repository.po.UserPO;
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
