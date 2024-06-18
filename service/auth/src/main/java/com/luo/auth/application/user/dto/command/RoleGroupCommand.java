package com.luo.auth.application.user.dto.command;

import lombok.Data;

import java.util.List;

@Data
public class RoleGroupCommand {
    private Long roleGroupId;
    private String roleGroupName;
    private List<Long> roleIds;
}
