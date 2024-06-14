package com.luo.auth.domain.user.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/13
 */
@Data
public class RoleGroup {
    private Long roleGroupId;
    private List<Role> roles;
    /**
     * 保存角色列表
     */
    public void saveRoleGroup(List<Role> roleList){
        if (!roleList.isEmpty()){
            this.roles=roleList;
        }
    }
}
