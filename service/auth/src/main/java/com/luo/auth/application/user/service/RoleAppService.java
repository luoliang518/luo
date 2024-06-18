package com.luo.auth.application.user.service;

import com.luo.auth.application.user.dto.command.RoleGroupCommand;

public interface RoleAppService {
    void addOrEditRoleGroup(RoleGroupCommand roleGroupCommand);

}
