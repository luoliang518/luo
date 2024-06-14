package com.luo.auth.domain.user.repository;

import com.luo.auth.domain.user.entity.User;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
public interface UserRepository {
    User getUserByAccount(String account);
}
