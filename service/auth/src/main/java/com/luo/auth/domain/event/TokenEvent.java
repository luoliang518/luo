package com.luo.auth.domain.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/7/15
 */
public class TokenEvent extends ApplicationEvent {
    private final String token;
    public TokenEvent(Object source, String token) {
        super(source);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
