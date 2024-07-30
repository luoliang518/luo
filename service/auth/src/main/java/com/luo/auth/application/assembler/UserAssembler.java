package com.luo.auth.application.assembler;

import com.luo.auth.application.dto.command.VerificationCodeCommand;
import com.luo.auth.application.dto.command.UserRegistrationCommand;
import com.luo.auth.application.dto.query.UserQuery;
import com.luo.auth.application.dto.vo.UserCodeVo;
import com.luo.auth.application.dto.vo.UserVO;
import com.luo.auth.domain.messageAggergate.valueObject.VerificationCode;
import com.luo.auth.domain.userAggregate.valueObject.Token;
import com.luo.auth.domain.userAggregate.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserAssembler {
    private final TenantAssembler tenantAssembler;
    public VerificationCode assembleVerificationCode(VerificationCodeCommand verificationCodeCommand){
        return new VerificationCode(verificationCodeCommand.getPhoneNumber(),
                verificationCodeCommand.getEmail(),
                verificationCodeCommand.getCode());
    }
    public UserCodeVo assembleUserCodeVo(VerificationCode verificationCode) {
        return UserCodeVo.builder()
                .account(verificationCode.getAccount())
                .email(verificationCode.getEmailMessage().getEmail())
                .expiration(verificationCode.getExpiration())
                .build();
    }
    public User assembleUser(UserRegistrationCommand userRegistrationCommand) {
        return User.builder()
                .account(userRegistrationCommand.getAccount())
                .username(userRegistrationCommand.getUsername())
                .email(userRegistrationCommand.getVerificationCodeCommand().getEmail())
                .phone(userRegistrationCommand.getVerificationCodeCommand().getPhoneNumber())
                .password(userRegistrationCommand.getPassword())
                .build();
    }
    public User assembleUser(UserQuery userQuery, String ip, String token) {
        return User.builder()
                .account(userQuery.getAccount())
                .password(userQuery.getPassword())
                .ip(ip)
                .token(new Token().setToken(token))
                .build();
    }
    public UserVO assembleUserVO(User user) {
        return UserVO.builder()
                .userId(user.getUserId())
                .account(user.getAccount())
                .username(user.getUsername())
                .token(user.getToken())
                .tenantVos(
                        tenantAssembler.assembleTenantVo(user.getTenants())
                )
                .build();
    }
}
