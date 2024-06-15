package com.luo.auth.infrastructure.converter;


import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.repository.po.UserPO;
import org.mapstruct.Mapper;

/**
 * @author luoliang
 */
@Mapper(componentModel = "spring")
public interface UserConverter {
//    @Mapping(source = "id", target = "userId")
//    @Mapping(target = "tenants", ignore = true)
//    @Mapping(target = "roleGroups", ignore = true)
//    @Mapping(target = "token", ignore = true)
    User userPoToUser(UserPO userPO);
}
