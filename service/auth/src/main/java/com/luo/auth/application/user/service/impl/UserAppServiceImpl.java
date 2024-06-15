package com.luo.auth.application.user.service.impl;

import com.luo.auth.application.user.dto.command.UserRegistrationCommand;
import com.luo.auth.application.user.dto.vo.UserCodeVo;
import com.luo.auth.application.user.service.UserAppService;
import com.luo.auth.domain.userAggregate.acl.AuthenticationAcl;
import com.luo.auth.domain.utilAggergate.entity.Token;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.repository.UserRepository;
import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.application.user.dto.vo.UserVO;
import com.luo.auth.domain.utilAggergate.service.EmailSenderService;
import com.luo.auth.infrastructure.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final JwtUtil jwtUtil;
    private final EmailSenderService emailSenderService;
    @Override
    public UserCodeVo sendCode(UserRegistrationCommand userRegistrationCommand) {
        String code = emailSenderService.sendVerificationCode(userRegistrationCommand.getEmail());
        return UserCodeVo.builder()
                .account(userRegistrationCommand.getAccount())
                .email(userRegistrationCommand.getEmail())
                .code(code).build();
    }
    @Override
    public UserVO userLogin(UserQuery userQuery) {
        User userByAccount = userRepository.getUserByAccount(userQuery.getAccount());
        assert userByAccount==null:"该账号不存在";
        User user = authenticationAcl.authUser(userQuery);
        String token = jwtUtil.generateToken(user);
        user.setToken(Token.builder().token(token).build());
        return new UserVO(user);
    }




}
