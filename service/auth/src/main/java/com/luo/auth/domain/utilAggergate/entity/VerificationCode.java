package com.luo.auth.domain.utilAggergate.entity;

import lombok.Data;
import lombok.Getter;

import java.security.SecureRandom;

@Getter
public class VerificationCode {
    private final String code;
    private static final String CHARACTERS = "0123456789";
    private static final int CODE_LENGTH = 6;

    public VerificationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(new SecureRandom().nextInt(CHARACTERS.length())));
        }
        this.code=code.toString();
    }

}
