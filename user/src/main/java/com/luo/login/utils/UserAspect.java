package com.luo.login.utils;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {
    @Before("execution(* com.luo.login.utils.UserBaseServiceImpl.sout())")
    public void beforeTest() {
        System.out.println("UserBaseServiceImpl beforeTest");
    }

    @After("execution(* com.luo.login.utils.UserBaseServiceImpl.sout())")
    public void afterTest() {
        System.out.println("UserBaseServiceImpl afterTest");
    }
}
