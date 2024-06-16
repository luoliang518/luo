package com.luo.auth.domain.userAggregate.service;

import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.domain.userAggregate.acl.AuthenticationAcl;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.repository.UserRepository;
import com.luo.auth.domain.utilAggergate.entity.Token;
import com.luo.auth.infrastructure.converter.UserConverter;
import com.luo.auth.infrastructure.util.JwtUtil;
import com.luo.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final AuthenticationAcl authenticationAcl;
    private final JwtUtil jwtUtil;
    private final UserConverter userConverter;

    public User authUser(UserQuery userQuery) {
        User userByAccount = userRepository.getUserByAccount(userQuery.getAccount());
        if (userByAccount == null) {
            throw new ServiceException("该账号不存在");
        }
        User user = authenticationAcl.authUser(userQuery);
        String token = jwtUtil.generateToken(user);
        user.setToken(Token.builder().token(token).build());
        return user;
    }

    public void userRegistration(User user) {
        user.checkUserInfo();
        user.encodePassword();
        userRepository.save(userConverter.userToUserPo(user));
    }

}
