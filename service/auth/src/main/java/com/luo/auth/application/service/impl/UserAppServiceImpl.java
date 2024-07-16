package com.luo.auth.application.service.impl;

import com.luo.auth.application.assembler.UserAssembler;
import com.luo.auth.application.dto.command.UserRegistrationCommand;
import com.luo.auth.application.dto.command.VerificationCodeCommand;
import com.luo.auth.application.dto.vo.UserCodeVo;
import com.luo.auth.application.service.UserAppService;
import com.luo.auth.application.dto.query.UserQuery;
import com.luo.auth.application.dto.vo.UserVO;
import com.luo.auth.domain.messageAggergate.valueObject.VerificationCode;
import com.luo.auth.domain.messageAggergate.service.EmailSenderService;
import com.luo.auth.domain.tenantAggregate.service.TenantService;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.service.UserService;
import com.luo.auth.infrastructure.util.IPUtil;
import com.luo.auth.infrastructure.util.RequestUtil;
import com.luo.common.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Service
@AllArgsConstructor
public class UserAppServiceImpl implements UserAppService {
    private final UserService userService;
    private final TenantService tenantService;
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
        Optional.of(userRegistrationCommand)
                // 将用户注册命令映射为验证码命令
                .map(cmd -> userAssembler.assembleVerificationCode(cmd.getVerificationCodeCommand()))
                // 过滤掉验证失败的情况
                .filter(emailSenderService::verifyVerificationCode)
                // 如果验证成功，进行用户注册
                .map(cmd -> {
                    userService.userRegistration(userAssembler.assembleUser(userRegistrationCommand));
                    return cmd;
                })
                // 如果没有通过过滤，则抛出异常
                .orElseThrow(() -> new ServiceException("验证码错误"));
    }

    @Override
    public UserVO userLogin(UserQuery userQuery, HttpServletRequest request) {
        // 获取ip
        String ip = IPUtil.getip(request);
        // 尝试获取token
        String tokenHeader = RequestUtil.getTokenHeader(request);
        // 认证用户
        User user = userService.authUser(userAssembler.assembleUser(userQuery,ip,tokenHeader));
        // 认证用户所在租户 并且存入缓存
        return userAssembler.assembleUserVO(tenantService.authTenant(user));
    }
}
