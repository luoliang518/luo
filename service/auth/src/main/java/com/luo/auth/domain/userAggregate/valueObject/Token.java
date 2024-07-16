package com.luo.auth.domain.userAggregate.valueObject;

import com.luo.common.constant.TokenConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
public class Token implements Serializable {
    private String token;
    // 续命令牌
    private String refreshToken;
    // token过期时间
    private Date tokenExpirationTime;
    private final Long tokenSurvivalTime = TokenConstant.TOKEN_SURVIVAL_TIME;
    private final Long tokenRefreshTime = TokenConstant.TOKEN_REFRESH_TIME;
    public Date getTokenExpirationTime() {
        long currentTime = new Date().getTime();
        this.tokenExpirationTime = new Date(currentTime + tokenRefreshTime);
//        this.tokenExpirationTime = new Date(currentTime + tokenSurvivalTime); todo
        return this.tokenExpirationTime;
    }

    public Date getTokenSurvivalTimeWithNow() {
        long deathTime = tokenExpirationTime.getTime();
        long currentTime = new Date().getTime();
        return new Date(deathTime - currentTime);
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }
}
