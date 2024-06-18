package com.luo.auth.domain.roleAggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleGroup {
    private Long roleGroupId;
    private String roleGroupName;
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
