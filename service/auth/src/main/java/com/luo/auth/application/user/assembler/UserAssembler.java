package com.luo.auth.application.user.assembler;

import com.luo.auth.application.user.dto.command.UserRegistrationCommand;
import com.luo.auth.application.user.dto.command.VerificationCodeCommand;
import com.luo.auth.application.user.dto.vo.UserCodeVo;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.utilAggergate.entity.VerificationCode;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {
    public VerificationCode assembleVerificationCode(VerificationCodeCommand verificationCodeCommand){
        return VerificationCode.builder()
                .build()
                .setEmailMessage(verificationCodeCommand.getEmail())
                .setPhoneMessage(verificationCodeCommand.getPhoneNumber());
    }
    public VerificationCode assembleVerificationCode(UserRegistrationCommand userRegistrationCommand){
        return VerificationCode.builder()
                .build()
                .setEmailMessage(userRegistrationCommand.getEmail())
                .setPhoneMessage(userRegistrationCommand.getPhoneNumber());
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
                .email(userRegistrationCommand.getEmail())
                .phone(userRegistrationCommand.getPhoneNumber())
                .password(userRegistrationCommand.getPassword())
                .build();
    }
}
