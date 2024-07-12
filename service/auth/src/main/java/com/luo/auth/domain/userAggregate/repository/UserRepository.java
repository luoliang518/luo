package com.luo.auth.domain.userAggregate.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.repository.po.UserPO;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
public interface UserRepository extends IService<UserPO> {
    User getUserByAccount(String account);

    User getUserRoleGroup(User user);

    User getUserRole(User user);

    User getUserPermission(User user);
}
