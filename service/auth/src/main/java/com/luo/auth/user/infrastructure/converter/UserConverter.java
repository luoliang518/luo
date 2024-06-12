package com.luo.auth.user.infrastructure.converter;


import com.luo.auth.user.domain.user.entity.User;
import com.luo.auth.user.infrastructure.repository.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserConverter {
    User userPoToUser(UserPO userPO);
}
