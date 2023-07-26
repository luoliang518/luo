package com.luo.user.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class UserBaseServiceImpl implements UserBaseService{
    @Override
    public String sout() {
        return "111111111111111";
    }

    public UserBaseServiceImpl() {
    }
    private String beanName;

}
