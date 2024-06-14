package com.luo.auth.application.user.service.impl;

import com.luo.auth.application.user.service.UserAppService;
import com.luo.auth.domain.user.acl.AuthenticationAcl;
import com.luo.auth.domain.user.entity.User;
import com.luo.auth.domain.user.repository.UserRepository;
import com.luo.auth.domain.user.repository.impl.UserRepositoryImpl;
import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.application.user.dto.vo.UserVO;
import com.luo.auth.infrastructure.user.converter.UserSecurity;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Service
@AllArgsConstructor
public class UserAppServiceImpl implements UserAppService {
    private final UserRepository userRepository;
    private final AuthenticationAcl authenticationAcl;
    @Override
    public UserVO userLogin(UserQuery userQuery) {
        User userByAccount = userRepository.getUserByAccount(userQuery.getAccount());
        assert userByAccount==null:"该账号不存在";
        UserSecurity userSecurity = authenticationAcl.authUser(userQuery);
        OAuth2ResourceServerProperties.Jwt jwt = new OAuth2ResourceServerProperties.Jwt();
        return null;
    }


}
