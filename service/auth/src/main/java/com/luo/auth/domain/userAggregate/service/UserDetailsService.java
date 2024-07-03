package com.luo.auth.domain.userAggregate.service;

import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.repository.UserRepository;
import com.luo.auth.infrastructure.config.security.dto.UserSecurity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userRepository.getUserByAccount(account);
        return new UserSecurity(user);
    }
}
