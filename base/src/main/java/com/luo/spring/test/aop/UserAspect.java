package com.luo.spring.test.aop;

import com.luo.spring.aop.anno.LuoAfter;
import com.luo.spring.aop.anno.LuoAspect;
import com.luo.spring.aop.anno.LuoBefore;
import com.luo.spring.component.LuoComponent;

@LuoAspect
@LuoComponent
public class UserAspect {
    @LuoBefore("execution(* *.UserBaseServiceImpl.sout())")
    public void beforeTest() {
        System.out.println("UserBaseServiceImpl beforeTest");
    }

    @LuoAfter("execution(* *.UserBaseServiceImpl.*())")
    public void afterTest() {
        System.out.println("UserBaseServiceImpl afterTest");
    }
}
