package com.luo.auth.application.user.dto.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCodeVo {
    private String account;
    private String phoneNumber;
    private String email;
    private String code;
    private String expiration;
}
