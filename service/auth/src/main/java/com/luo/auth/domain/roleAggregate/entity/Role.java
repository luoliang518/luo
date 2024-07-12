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
public class Role {
    private Long roleId;
    private String roleName;
    private List<Permission> permissions;
    /**
     * 保存权限列表
     */
    public void saveRoleGroup(List<Permission> permissionList){
        if (!permissionList.isEmpty()){
            this.permissions=permissionList;
        }
    }
}
