package com.luo.auth.domain.user.service;

import com.luo.auth.infrastructure.user.converter.UserSecurity;
import com.luo.auth.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/13
 */
//@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        return new UserSecurity(userRepository.getUserByAccount(account));
    }
}
