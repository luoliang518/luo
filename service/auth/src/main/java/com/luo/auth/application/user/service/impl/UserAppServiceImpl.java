package com.luo.auth.application.user.service.impl;

import com.luo.auth.application.user.assembler.UserAssembler;
import com.luo.auth.application.user.dto.command.UserRegistrationCommand;
import com.luo.auth.application.user.dto.command.VerificationCodeCommand;
import com.luo.auth.application.user.dto.vo.UserCodeVo;
import com.luo.auth.application.user.service.UserAppService;
import com.luo.auth.domain.userAggregate.service.UserServiceImpl;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.application.user.dto.vo.UserVO;
import com.luo.auth.domain.utilAggergate.entity.VerificationCode;
import com.luo.auth.domain.utilAggergate.service.EmailSenderService;
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
    private final UserServiceImpl userService;
    private final EmailSenderService emailSenderService;
    private final UserAssembler userAssembler;

    @Override
    public UserCodeVo sendCode(VerificationCodeCommand verificationCodeCommand) {
        // 发送邮件
        VerificationCode verificationCode = emailSenderService.sendVerificationCode(
                userAssembler.assembleVerificationCode(verificationCodeCommand)
        );
        // 返回前端
        return userAssembler.assembleUserCodeVo(verificationCode);
    }

    @Override
    public void userRegistration(UserRegistrationCommand userRegistrationCommand) {
        // 校验验证码
        emailSenderService.verifyVerificationCode(
                userAssembler.assembleVerificationCode(userRegistrationCommand)
        );
        // 保存用户
        userService.userRegistration(userAssembler.assembleUser(userRegistrationCommand));
    }

    @Override
    public UserVO userLogin(UserQuery userQuery) {
        User user = userService.authUser(userQuery);
        return new UserVO(user);
    }

}
