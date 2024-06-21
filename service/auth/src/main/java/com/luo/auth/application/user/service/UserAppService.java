package com.luo.auth.application.user.service;

import com.luo.auth.application.user.dto.command.UserRegistrationCommand;
import com.luo.auth.application.user.dto.command.VerificationCodeCommand;
import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.application.user.dto.vo.UserCodeVo;
import com.luo.auth.application.user.dto.vo.UserVO;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
public interface UserAppService {
    UserCodeVo sendCode(VerificationCodeCommand verificationCodeCommand);

    void userRegistration(UserRegistrationCommand userRegistrationCommand);

    UserVO userLogin(UserQuery userQuery);

    void choiceTenant(Long tenantId);
}