package com.luo.auth.domain.userAggregate.service;

import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.repository.UserRepository;
import com.luo.auth.domain.userAggregate.valueObject.Token;
import com.luo.auth.infrastructure.acl.AuthAcl;
import com.luo.auth.infrastructure.acl.CacheAcl;
import com.luo.auth.infrastructure.acl.TokenAcl;
import com.luo.auth.infrastructure.converter.UserConverter;
import com.luo.auth.infrastructure.util.IPUtil;
import com.luo.auth.infrastructure.util.RequestUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final AuthAcl authAcl;
    private final TokenAcl tokenAcl;
    private final CacheAcl cacheAcl;

    public void userRegistration(User user) {
        user.checkUserInfo();
        user.encodePassword();
        userRepository.save(userConverter.userToUserPo(user));
    }

    /**
     * 根据token+ip 或者 账号+密码获取用户
     * @param user
     * @return
     */
    public User authUser(User user) {
        // 获取用户信息
        user = getUser(user);
        User finalUser = user;
        // 防止并发生成多个token
        return Optional.ofNullable(cacheAcl.getUserLock(user)).orElseGet(() -> {
            // 判断是否已有token
            Token oldToken = finalUser.getToken();
            String refreshToken = oldToken.getToken();
            oldToken.setRefreshToken(refreshToken);
            // 分配token
            String token = tokenAcl.generateToken(finalUser);
            oldToken.setToken(token);
            // 存入缓存
            cacheAcl.saveUserTokenCache(finalUser);
            return finalUser;
        });
    }
    public User initUserRole(User user) {
        user = userRepository.getUserRoleGroup(user);
        user = userRepository.getUserRole(user);
        user = userRepository.getUserPermission(user);
        // 存入缓存
        cacheAcl.saveUserTokenCache(user);
        return user;
    }
    private User getUser(User user) {
        // 判断请求头是否拥有token
        switch (user.getToken().getToken()) {
            case String token ->{
                // 校验令牌
                Jws<Claims> jwt = tokenAcl.tokenAnalysis(token);
                // 使用redis优化
                return  cacheAcl.getUserInfo(jwt.getBody().getSubject(), user.getIp());
            }
            case null ->{
                // 第一次登录获取用户信息
                user = authAcl.authUser(user);
                return user;
            }
        }
    }
}
