package com.luo.common.context.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserContext {
    private Long userId;
    private String account;
    private String username;
}
