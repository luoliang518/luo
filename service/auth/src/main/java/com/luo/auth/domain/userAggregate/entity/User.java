package com.luo.auth.domain.userAggregate.entity;

import com.luo.auth.domain.tenantAggregate.entity.Tenant;
import com.luo.auth.domain.utilAggergate.entity.Token;
import com.luo.auth.infrastructure.converter.PermissionSecurity;
import lombok.Data;

import java.util.List;

/**
 * @author luoliang
 */
@Data
public class User {
    private Long userId;
    // 用户名
    private String username;
    // 账号
    private String account;
    // 密码
    private String password;
    // 头像
    private String avatar;
    // 邮箱
    private String email;
    // 手机号
    private String phone;
    // 租户
    private List<Tenant> tenants;
    // 角色
    private List<RoleGroup> roleGroups;
    // token
    private Token token;
    /**
     * 获取权限列表
     */
    public List<Permission> getPermissionList(){
        return this.getRoleGroups().stream()
                .flatMap(roleGroup -> roleGroup.getRoles().stream())
                .flatMap(role -> role.getPermissions().stream())
                .toList();
    }
    /**
     * 获取安全校验权限列表
     */
    public List<PermissionSecurity> getPermissionSecurityList(){
        return this.getPermissionList().stream()
                .map(PermissionSecurity::new)
                .toList();
    }
    /**
     * 保存用户角色组
     */
    public void saveRoleGroup(List<RoleGroup> roleGroupList){
        if (!roleGroupList.isEmpty()){
            this.roleGroups=roleGroupList;
        }
    }
}
