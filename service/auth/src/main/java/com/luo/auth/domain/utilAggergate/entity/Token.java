package com.luo.auth.domain.utilAggergate.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Token {
    private String token;
    private String refreshToken;
    private Date expires;
}
