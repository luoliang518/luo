package com.luo.auth.domain.utilAggergate.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.security.SecureRandom;

@Getter
@Builder
public class VerificationCode implements Serializable {
    private static final String CHARACTERS = "0123456789";
    private static final int CODE_LENGTH = 6;

    private String account;
    private String username;
    private PhoneMessage phoneMessage;
    private EmailMessage emailMessage;
    private String code;
    private Long expiration = 600L;

    public void setVerificationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(new SecureRandom().nextInt(CHARACTERS.length())));
        }
        this.code=code.toString();
    }

    public VerificationCode setEmailMessage(String email) {
        this.emailMessage = new EmailMessage(email);
        return this;
    }
    public VerificationCode setPhoneMessage(String phoneNumber) {
        this.phoneMessage = new PhoneMessage(phoneNumber);
        return this;
    }

    public VerificationCode setExpiration(Long expiration) {
        this.expiration = expiration;
        return this;
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", phoneMessage=" + phoneMessage +
                ", emailMessage=" + emailMessage +
                ", code='" + code + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}
