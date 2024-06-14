package com.luo.auth.infrastructure.user.converter;

import com.luo.auth.domain.user.entity.Permission;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/13
 */
@AllArgsConstructor
public class PermissionSecurity implements GrantedAuthority {
    private Permission permission;
    @Override
    public String getAuthority() {
        return permission.getPermissionCode();
    }
}
