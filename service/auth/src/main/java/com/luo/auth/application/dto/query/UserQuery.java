package com.luo.auth.application.dto.query;

import lombok.Data;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Data
public class UserQuery {
    private String account;
    private String password;
}
