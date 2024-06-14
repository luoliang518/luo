package com.luo.auth.infrastructure.user.converter;


import com.luo.auth.domain.user.entity.User;
import com.luo.auth.infrastructure.user.repository.po.UserPO;
import org.mapstruct.Mapper;
/**
 * @author luoliang
 */
@Mapper(componentModel = "spring")
public interface UserConverter {
    User userPoToUser(UserPO userPO);
}
