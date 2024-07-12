package com.luo.auth.infrastructure.config.security.dto;

import com.luo.auth.domain.userAggregate.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class UserSecurity extends org.springframework.security.core.userdetails.User {
    private User user;

    public UserSecurity(User user) {
        super(user.getAccount(), user.getPassword(),user.getPermissionSecurityList());
        this.user = user;
    }

    public UserSecurity(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
