package com.luo.auth.application.service.impl;

import com.luo.auth.application.assembler.RoleAssembler;
import com.luo.auth.application.dto.command.RoleGroupCommand;
import com.luo.auth.application.service.RoleAppService;
import com.luo.auth.application.dto.vo.RoleGroupVO;
import com.luo.auth.domain.roleAggregate.entity.RoleGroup;
import com.luo.auth.domain.roleAggregate.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleAppServiceImpl implements RoleAppService {
    private final RoleService roleService;
    private final RoleAssembler roleAssembler;
    @Override
    public void addOrEditRoleGroup(RoleGroupCommand roleGroupCommand) {
        roleService.addOrEditRoleGroup(
                roleAssembler.assembleRoleGroup(roleGroupCommand)
        );
    }

    @Override
    public List<RoleGroupVO> getRoleGroupList() {
        List<RoleGroup> roleGroups = roleService.getRoleGroupList();
        return roleAssembler.assembleRoleGroupVOs(roleGroups);
    }
}
