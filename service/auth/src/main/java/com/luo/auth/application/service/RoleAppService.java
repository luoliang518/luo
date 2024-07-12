package com.luo.auth.application.service;

import com.luo.auth.application.dto.command.RoleGroupCommand;
import com.luo.auth.application.dto.vo.RoleGroupVO;

import java.util.List;

public interface RoleAppService {
    void addOrEditRoleGroup(RoleGroupCommand roleGroupCommand);

    List<RoleGroupVO> getRoleGroupList();
}
