package com.luo.auth.domain.userAggregate.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Token implements Serializable {
    private String token;
    private String refreshToken;
    private Long expires ;
}
