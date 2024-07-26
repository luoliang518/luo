package com.luo.auth.domain.roleAggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleGroup {
    private Long roleGroupId;
    private String roelGroupCode;
    private String roleGroupName;
    private List<Role> roles;
}
