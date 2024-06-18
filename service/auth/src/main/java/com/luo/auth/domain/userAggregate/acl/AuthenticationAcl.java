package com.luo.auth.domain.userAggregate.acl;

import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.config.security.UserSecurity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Component
@AllArgsConstructor
public class AuthenticationAcl {
    private final AuthenticationManager authenticationManager;
    public User authUser(UserQuery userQuery) {
        // 校验用户
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userQuery.getAccount(), userQuery.getPassword());
        // 调用自定义用户校验方法
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        assert authenticate==null:"该账号密码错误";
        // 校验成功，强转对象
        return ((UserSecurity) authenticate.getPrincipal()).getUser();
    }
}
