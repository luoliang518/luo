package com.luo.login.service;

import com.luo.model.user.dto.UserDto;

public interface UserServiceV2 extends UserService{
    public void createUser(UserDto user);
}
