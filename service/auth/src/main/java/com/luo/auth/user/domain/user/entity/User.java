package com.luo.auth.user.domain.user.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author luoliang
 */
@Data
public class User {
    private Long id;
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
//    private Tenant tenant;
    // 角色
    private List<RoleGroup> roleGroups;
}
