package com.luo.auth.domain.userAggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {
    private String token;
    private String refreshToken;
    private Long expires ;
}
