package com.luo.auth.user.infrastructure.converter;

import com.luo.auth.user.infrastructure.repository.po.UserPO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
@Getter
public class UserSecurity extends User {
    private UserPO userPO;

    public UserSecurity(UserPO userPO, Collection<? extends GrantedAuthority> authorities) {
        super(userPO.getAccount(), userPO.getPassword(), authorities);
        this.userPO = userPO;
    }

    public UserSecurity(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

}
