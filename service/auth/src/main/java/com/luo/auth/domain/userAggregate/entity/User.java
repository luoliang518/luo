package com.luo.auth.domain.userAggregate.entity;

import com.luo.auth.domain.tenantAggregate.entity.Tenant;
import com.luo.auth.domain.utilAggergate.entity.Token;
import com.luo.auth.infrastructure.converter.PermissionSecurity;
import com.luo.common.exception.ServiceException;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author luoliang
 */
@Data
@Builder
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
    public List<Permission> getPermissionList() {
        return this.getRoleGroups().stream()
                .flatMap(roleGroup -> roleGroup.getRoles().stream())
                .flatMap(role -> role.getPermissions().stream())
                .toList();
    }

    /**
     * 获取安全校验权限列表
     */
    public List<PermissionSecurity> getPermissionSecurityList() {
        return this.getPermissionList().stream()
                .map(PermissionSecurity::new)
                .toList();
    }
    /**
     * 校验用户信息
     */
    public void checkUserInfo() {
        if (this.account!=null && !"[a-zA-Z0-9]+".matches(this.account)){
            throw new ServiceException("请输入大小写字母及数字作为用户名");
        }
        if (this.password!=null && !"[a-zA-Z0-9]+".matches(this.password)){
            throw new ServiceException("请输入大小写字母及数字作为密码");
        }
        if (this.username!=null && !"[a-zA-Z0-9\u4e00-\u9fa5]+".matches(this.username)){
            throw new ServiceException("请输入中文字符、大小写字母或数字作为用户名");
        }
        if (this.email!=null &&
                !"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,6}$".matches(this.email)){
            throw new ServiceException("请输入正确邮箱");
        }
        if (this.phone!=null &&
                !"^1[3-9]\\d{9}$".matches(this.phone)){
            throw new ServiceException("请输入正确手机号");
        }
    }
    /**
     * 加密密码
     */
    public void encodePassword() {
        if (StringUtils.hasText(this.password) &&
                !(
                        this.password.startsWith("$2a$") ||
                        this.password.startsWith("$2b$") ||
                        this.password.startsWith("$2y$")
                )) {
            this.password = new BCryptPasswordEncoder().encode(this.password);
        }
    }

    /**
     * 保存用户角色组
     */
    public void saveRoleGroup(List<RoleGroup> roleGroupList) {
        if (!roleGroupList.isEmpty()) {
            this.roleGroups = roleGroupList;
        }
    }
}
