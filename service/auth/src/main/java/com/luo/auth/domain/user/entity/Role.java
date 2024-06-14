package com.luo.auth.domain.user.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/13
 */
@Data
public class Role {
    private Long roleId;
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
