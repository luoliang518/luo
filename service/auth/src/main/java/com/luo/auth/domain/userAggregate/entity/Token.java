package com.luo.auth.domain.userAggregate.entity;

import com.luo.common.constant.TokenConstant;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Token {
    private String token;
    private String refreshToken;
    private Long expires ;
}
