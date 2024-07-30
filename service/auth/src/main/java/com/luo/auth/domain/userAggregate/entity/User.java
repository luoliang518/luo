package com.luo.auth.domain.userAggregate.entity;

import com.luo.auth.domain.roleAggregate.entity.Permission;
import com.luo.auth.domain.roleAggregate.entity.RoleGroup;
import com.luo.auth.domain.tenantAggregate.entity.Tenant;
import com.luo.auth.domain.userAggregate.valueObject.Token;
import com.luo.auth.infrastructure.config.security.dto.PermissionSecurity;
import com.luo.common.constant.TokenConstant;
import com.luo.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author luoliang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    // 用户名
    private String username;
    // 账号
    private String account;
    // 密码
    private String password;
    // ip
    private String ip;
    // 头像
    private String avatar;
    // 邮箱
    private String email;
    // 手机号
    private String phone;
    // 当前所属租户
    private Tenant currentTenant;
    // 租户
    private List<Tenant> tenants;
    // 角色
    private List<RoleGroup> roleGroups;
    // token
    private Token token;
    /**
     * 获取权限列表 为空返回空列表
     */
    public List<Permission> getPermissionList() {
        return Optional.ofNullable(this.getRoleGroups())
                .stream()
                .flatMap(List::stream)
                .flatMap(roleGroup -> roleGroup.getRoles().stream())
                .flatMap(role -> role.getPermissions().stream())
                .toList();
    }

    /**
     * 获取安全校验权限列表
     */
    public List<PermissionSecurity> getPermissionSecurityList() {
        return this.getPermissionList().stream()
                .filter(Objects::nonNull)
                .map(PermissionSecurity::new)
                .toList();
    }
    /**
     * 校验用户信息
     */
    public void checkUserInfo() {
        if (StringUtils.hasText(this.account) &&
                !this.account.matches("[a-zA-Z0-9]+")) {
            throw new ServiceException("请输入大小写字母或数字作为账号");
        }
        if (StringUtils.hasText(this.password) &&
                !this.password.matches("[a-zA-Z0-9]+")){
            throw new ServiceException("请输入大小写字母及数字作为密码");
        }
        if (StringUtils.hasText(this.username)&&
                !this.username.matches("[a-zA-Z0-9\u4e00-\u9fa5]+")){
            throw new ServiceException("请输入中文字符、大小写字母或数字作为用户名");
        }
        if (StringUtils.hasText(this.email)&&
                !this.email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new ServiceException("请输入正确邮箱");
        }
        if (StringUtils.hasText(this.phone) &&
                !this.phone.matches("^1[3-9]\\d{9}$")){
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

    public Token getToken() {
        if (this.token==null){
            this.token = new Token();
        }
        return this.token;
    }
}
