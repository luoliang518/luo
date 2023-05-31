package com.luo.spring.test.service.impl;

import com.luo.spring.bean.LuoBeanNameAware;
import com.luo.spring.bean.LuoDisposableBean;
import com.luo.spring.bean.LuoInitializingBean;
import com.luo.spring.component.LuoAutowired;
import com.luo.spring.component.LuoComponent;
import com.luo.spring.component.LuoScope;
import com.luo.spring.test.service.UserBaseService;
import com.luo.spring.test.service.UserBaseV2Service;

@LuoComponent
@LuoScope("singleton")
public class UserBaseV2ServiceImpl implements UserBaseV2Service, LuoBeanNameAware, LuoInitializingBean, LuoDisposableBean {
    @LuoAutowired
    private UserBaseService userBaseService;

    private String beanName;

    private String name;
    @Override
    public void setBeanName(String name) {
        this.beanName= name;
    }

    @Override
    public void afterPropertiesSet() {
        // do something
        System.out.println("UserBaseV2ServiceImpl 初始化");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("UserBaseV2ServiceImpl 销毁");
    }

    @Override
    public String sout() {
        return name+"       "+userBaseService.sout();
    }
    public String getBeanName() {
        return beanName;
    }

    public UserBaseV2ServiceImpl() {
    }

    public UserBaseService getUserBaseService() {
        return userBaseService;
    }

    public void setUserBaseService(UserBaseService userBaseService) {
        this.userBaseService = userBaseService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
