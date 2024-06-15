package com.luo.auth.domain.userAggregate.repository;

import com.luo.auth.domain.userAggregate.entity.User;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
public interface UserRepository {
    User getUserByAccount(String account);
}
