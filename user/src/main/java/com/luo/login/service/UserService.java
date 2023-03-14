package com.luo.login.service;

import com.luo.model.user.UserDto;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 10:25
 */
public interface UserService {
    /**
     * 创建用户
     * @param user
     */
    void createUser(UserDto user);
}
