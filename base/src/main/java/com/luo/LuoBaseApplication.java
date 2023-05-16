package com.luo;

import com.luo.spring.UserBeanConfig;
import com.luo.spring.application.LuoApplicationContext;

public class LuoBaseApplication {
    public static void main(String[] args) throws ClassNotFoundException {
        LuoApplicationContext luoApplicationContext = new LuoApplicationContext(UserBeanConfig.class);
        System.out.println(luoApplicationContext.getBean("userBaseServiceImpl"));
        System.out.println(luoApplicationContext.getBean("userBaseServiceImpl"));
    }
}