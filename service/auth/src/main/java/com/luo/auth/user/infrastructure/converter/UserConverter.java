package com.luo.auth.user.infrastructure.converter;

import com.luo.auth.user.infrastructure.repository.po.UserPO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
public class UserConverter extends User {
    private UserPO userPO;

    public UserConverter(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userPO.get, password, authorities);
    }

    public UserConverter(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
