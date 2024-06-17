package com.luo.auth.application.user.dto.command;

import lombok.Data;

@Data
public class UserRegistrationCommand {
    private String username;
    private String account;
    private String password;
    private VerificationCodeCommand verificationCodeCommand;
}
