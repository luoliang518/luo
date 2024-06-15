package com.luo.auth.infrastructure.converter;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserSecurity extends User {
    private com.luo.auth.domain.userAggregate.entity.User user;

    public UserSecurity(com.luo.auth.domain.userAggregate.entity.User user) {
        super(user.getAccount(), user.getPassword(),user.getPermissionSecurityList());
        this.user = user;
    }

    public UserSecurity(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
