package com.luo.spring.test.service.impl;

import com.luo.spring.bean.LuoBeanNameAware;
import com.luo.spring.component.LuoScope;
import com.luo.spring.component.LuoComponent;
import com.luo.spring.test.service.UserBaseService;

@LuoComponent
@LuoScope("singleton")
public class UserBaseServiceImpl implements UserBaseService, LuoBeanNameAware {
    @Override
    public String sout() {
        return "111111111111111";
    }

    public UserBaseServiceImpl() {
    }
    private String beanName;
    @Override
    public void setBeanName(String name) {
        this.beanName= name;
    }
}
