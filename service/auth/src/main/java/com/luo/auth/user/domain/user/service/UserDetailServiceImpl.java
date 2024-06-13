package com.luo.auth.user.domain.user.service;

import com.luo.auth.user.domain.user.entity.Permission;
import com.luo.auth.user.domain.user.entity.Role;
import com.luo.auth.user.domain.user.entity.RoleGroup;
import com.luo.auth.user.domain.user.entity.User;
import com.luo.auth.user.domain.user.repository.UserRepository;
import com.luo.auth.user.infrastructure.converter.PermissionSecurity;
import com.luo.auth.user.infrastructure.converter.UserSecurity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/13
 */
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        return new UserSecurity(userRepository.getUserByAccount(account));
    }
}
