package com.luo.login.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.luo.login.mapper.UserMapper;
import com.luo.login.mapper.UserRoleMapper;
import com.luo.model.user.UserDo;
import com.luo.model.user.UserRoleDo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 14:27
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 根据用户名获取用户 可传入账号或用户名
        UserDo userDo =
                userMapper.selectOne(new LambdaQueryWrapper<UserDo>()
                        .eq(UserDo::getAccount, account));
        String userRolesIds = userDo.getUserRolesIds();
        JSONArray array = JSONArray.parseArray(userRolesIds);

        List<UserRoleDo> userRoleDos = userRoleMapper.selectList(new LambdaQueryWrapper<UserRoleDo>().in(UserRoleDo::getRoleId, array));
        String[] s = new String[userRoleDos.size()];
        for (int i = 0; i < userRoleDos.size(); i++) {
            s[i] = userRoleDos.get(i).getRoleName();
        }
        return User.withUsername(userDo.getAccount())
                .password(userDo.getPassword())
                .roles(s)
                // 权限
//                .authorities()
                .disabled(StringPool.ONE.equals(userDo.getDeleted()))
                .build();
    }
}
