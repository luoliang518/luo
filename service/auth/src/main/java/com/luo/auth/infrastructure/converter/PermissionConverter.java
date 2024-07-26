package com.luo.auth.infrastructure.converter;


import com.luo.auth.domain.roleAggregate.entity.Permission;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.repository.po.PermissionPO;
import com.luo.auth.infrastructure.repository.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author luoliang
 */
@Mapper(componentModel = "spring")
public interface PermissionConverter {
    @Mapping(source = "id", target = "permissionId")
    Permission permissionPoToPermission(PermissionPO permissionPO);
    @Mapping(source = "id", target = "permissionId")
    List<Permission> permissionPosToPermissions(List<PermissionPO> permissionPOs);
}
