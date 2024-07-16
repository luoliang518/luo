package com.luo.auth.application.listener;

import com.luo.auth.domain.event.RefreshTokenEvent;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.service.UserService;
import com.luo.auth.domain.userAggregate.valueObject.Token;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/7/15
 */
@Slf4j
@Component
@AllArgsConstructor
public class TokenListener {
    private final UserService userService;
    @Async
    @Order(0)
    @EventListener
    public void createNewToken(RefreshTokenEvent event) {
        log.info("Received event - " + event.getToken());
        userService.authUser(User.builder()
                .token(new Token().setToken(event.getToken().substring(7)))
                .ip(event.getIp()).build());
    }
    @Async
    @Order(-1)
    @EventListener
    public void testNewToken(RefreshTokenEvent event) {
        try {
            log.info("222222");
            Thread.sleep(2000);
            log.info("3333333");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
