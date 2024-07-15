package com.luo.auth.application.listener;

import com.luo.auth.domain.event.TokenEvent;
import com.luo.auth.domain.userAggregate.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/7/15
 */
@Component
@AllArgsConstructor
public class TokenListener {
    private final UserService userService;
    @EventListener
    public void handleCustomEvent(TokenEvent event) {
        // 放入缓存新token处 如果新token已经有值，则移入token再赋予新token值
        System.out.println("Received event - " + event.getToken());
//        userService.authUser()
    }
}
