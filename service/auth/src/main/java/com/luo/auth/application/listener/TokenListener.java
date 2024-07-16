package com.luo.auth.application.listener;

import com.luo.auth.domain.event.RefreshTokenEvent;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.service.UserService;
import com.luo.auth.domain.userAggregate.valueObject.Token;
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
    public void createNewToken(RefreshTokenEvent event) {

        System.out.println("Received event - " + event.getToken());
        User user = userService.authUser(User.builder()
                .token(new Token().setToken(event.getToken().substring(7)))
                .ip(event.getIp()).build());

    }
}
