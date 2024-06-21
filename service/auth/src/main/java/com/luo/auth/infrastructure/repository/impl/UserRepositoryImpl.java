package com.luo.auth.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.repository.UserRepository;
import com.luo.auth.infrastructure.converter.UserConverter;
import com.luo.auth.infrastructure.repository.mapper.UserMapper;
import com.luo.auth.infrastructure.repository.po.UserPO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl extends ServiceImpl<UserMapper,UserPO> implements UserRepository {
    private final UserConverter userConverter;
    @Override
    public User getUserByAccount(String account) {
        Assert.notNull(account,"用户账号不能为空");
        UserPO userPO = getOne(lambdaQuery().eq(UserPO::getAccount, account));
        return switch (userPO) {
            case UserPO po -> userConverter.userPoToUser(po);
            case null -> null; // 或者根据需要抛出异常
        };
    }
}
