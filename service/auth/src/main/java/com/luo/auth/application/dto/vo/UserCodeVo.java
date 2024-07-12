package com.luo.auth.application.dto.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class UserCodeVo {
    private String account;
    private String phoneNumber;
    private String email;
    private String code;
    private Long expiration;

    public UserCodeVo setExpiration(Long expiration) {
        this.expiration = expiration;
        return this;
    }
}
