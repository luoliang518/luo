package com.luo.auth.infrastructure.acl;

import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.config.security.dto.UserSecurity;
import com.luo.common.exception.ServiceException;
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
public class AuthAcl {
    private final AuthenticationManager authenticationManager;
    public User authUser(User user) {
        // 校验用户
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user.getAccount(), user.getPassword());
        // 调用自定义用户校验方法
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (!authenticate.isAuthenticated()) {
            throw new ServiceException("账号或密码错误");
        }
        return ((UserSecurity) authenticate.getPrincipal()).getUser();
    }
}
