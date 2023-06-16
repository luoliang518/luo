package com.luo.login.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.luo.login.service.UserService;
import com.luo.model.user.entity.UserDo;
import com.luo.model.user.entity.UserRoleDo;
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
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        UserDo userDo = userService.getUserByAccount(account);
        List<UserRoleDo> userRoleDos = userDo.getUserRoleDos();
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
