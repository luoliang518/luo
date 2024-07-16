package com.luo.auth.domain.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/7/15
 */
@Getter
public class RefreshTokenEvent extends ApplicationEvent {
    private final String token;
    private final String ip;
    public RefreshTokenEvent(Object source, String token, String ip) {
        super(source);
        this.token = token;
        this.ip = ip;
    }
}
