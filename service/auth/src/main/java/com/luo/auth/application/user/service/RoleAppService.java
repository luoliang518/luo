package com.luo.auth.application.user.service;

import com.luo.auth.application.user.dto.command.RoleGroupCommand;
import com.luo.auth.application.user.dto.vo.RoleGroupVO;

import java.util.List;

public interface RoleAppService {
    void addOrEditRoleGroup(RoleGroupCommand roleGroupCommand);

    List<RoleGroupVO> getRoleGroupList();
}
