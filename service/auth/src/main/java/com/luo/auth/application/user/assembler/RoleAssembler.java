package com.luo.auth.application.user.assembler;

import com.luo.auth.application.user.dto.command.RoleGroupCommand;
import com.luo.auth.domain.roleAggregate.entity.Role;
import com.luo.auth.domain.roleAggregate.entity.RoleGroup;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleAssembler {
    public RoleGroup assembleRoleGroup(RoleGroupCommand roleGroupCommand){
        return new RoleGroup(
                roleGroupCommand.getRoleGroupId(),
                roleGroupCommand.getRoleGroupName(),
                roleGroupCommand.getRoleIds().stream()
                        .map(roleId -> Role.builder().roleId(roleId).build())
                        .collect(Collectors.toList())
        );
    }
}
