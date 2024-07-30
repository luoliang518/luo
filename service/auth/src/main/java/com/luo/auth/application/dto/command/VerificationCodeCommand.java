package com.luo.auth.application.dto.command;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class VerificationCodeCommand {
    private String phoneNumber;
    private String email;
    private String code;
}
