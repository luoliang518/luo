package com.luo.login.service;

import com.luo.model.user.dto.UserDto;

public interface UserServiceV3 extends UserService{
    public void createUser(UserDto user);
}
