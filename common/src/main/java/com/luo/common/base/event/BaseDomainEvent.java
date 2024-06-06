package com.luo.common.base.event;

import java.time.LocalDateTime;
import java.util.UUID;

/*
    所有event都应当继承该类
 */
public class BaseDomainEvent {
    /**
    * 领域事件唯一ID，该ID并不一定是实体（Entity）层面的ID
     */
    private final String id;
    /**
     * 用于追溯事件创建时间
     */
    private final LocalDateTime occurredOn;
    public BaseDomainEvent() {
        this.id = String.valueOf(UUID.randomUUID());
        this.occurredOn = LocalDateTime.now();
    }
}
