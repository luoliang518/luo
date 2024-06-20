package com.luo.auth.infrastructure.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luo.auth.domain.roleAggregate.entity.Role;
import com.luo.auth.domain.roleAggregate.entity.RoleGroup;
import com.luo.auth.infrastructure.repository.po.RoleGroupPO;
import com.luo.common.exception.ServiceException;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleGroupConverter {
    default RoleGroupPO roleGroupToRoleGroupPO(RoleGroup roleGroup) {
        RoleGroupPO build = null;
        try {
            build = RoleGroupPO.builder()
                    .roelGroupCode(roleGroup.getRoelGroupCode())
                    .roleGroupName(roleGroup.getRoleGroupName())
                    .roleIds(
                            new ObjectMapper().writeValueAsString(
                                    roleGroup.getRoles()
                                            .stream()
                                            .map(role -> String.valueOf(role.getRoleId()))
                                            .toList()
                            )
                    ).build();
        } catch (JsonProcessingException e) {
            throw new ServiceException("角色组中角色转换JSON异常");
        }
        build.setId(roleGroup.getRoleGroupId());
        return build;
    }

    default List<RoleGroup> roleGroupPOsToRoleGroup(List<RoleGroupPO> roleGroupPOS) {
        return roleGroupPOS.stream().map(roleGroupPO -> {
            try {
                return new RoleGroup(
                        roleGroupPO.getId(),
                        roleGroupPO.getRoleGroupName(),
                        roleGroupPO.getRoelGroupCode(),
                        // 处理角色id
                        new ObjectMapper().readValue(
                                roleGroupPO.getRoleIds(),
                                new TypeReference<List<String>>() {}
                        ).stream()
                                .map(roleId -> Role.builder().roleId(Long.valueOf(roleId)).build())
                                .toList()
                );
            } catch (JsonProcessingException e) {
                throw new ServiceException("角色组中角色转换JSON异常");
            }
        }).toList();
    }
}
