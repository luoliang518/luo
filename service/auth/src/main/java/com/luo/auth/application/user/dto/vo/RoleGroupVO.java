package com.luo.auth.application.user.dto.vo;

import com.luo.auth.domain.roleAggregate.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoleGroupVO {
    private Long roleGroupId;
    private String roelGroupCode;
    private String roleGroupName;
    private List<Role> roles;
}
