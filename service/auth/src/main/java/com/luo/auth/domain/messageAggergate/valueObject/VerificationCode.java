package com.luo.auth.domain.messageAggergate.valueObject;

import com.luo.common.constant.VerificationCodeConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.security.SecureRandom;

@Getter
@NoArgsConstructor
public class VerificationCode implements Serializable {


    private String account;
    private String username;
    private PhoneMessage phoneMessage;
    private EmailMessage emailMessage;
    private String code;
    private Long expiration = VerificationCodeConstant.VERIFICATION_CODE_SURVIVAL_TIME;

    public VerificationCode(String phone,String email,String code) {
        setPhoneMessage(phone);
        setEmailMessage(email);
        this.code = code;
    }

    public void setVerificationCode() {
        StringBuilder code = new StringBuilder(VerificationCodeConstant.CODE_LENGTH);
        for (int i = 0; i < VerificationCodeConstant.CODE_LENGTH; i++) {
            code.append(VerificationCodeConstant.CHARACTERS.charAt(new SecureRandom().nextInt(VerificationCodeConstant.CHARACTERS.length())));
        }
        this.code=code.toString();
    }

    public void setEmailMessage(String email) {
        this.emailMessage = new EmailMessage(email);
    }
    public void setPhoneMessage(String phoneNumber) {
        this.phoneMessage = new PhoneMessage(phoneNumber);
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
