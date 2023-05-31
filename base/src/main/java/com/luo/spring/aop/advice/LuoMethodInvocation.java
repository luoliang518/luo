package com.luo.spring.aop.advice;

import java.lang.reflect.Method;

public interface LuoMethodInvocation extends JoinPoint {

    /**
     * 获得执行链中目标方法的实参
     * @return
     */
    Object[] getArguments();

    /**
     * 修改执行链中目标方法的实参
     * ProxyMethodInvocation 中的功能，这里直接放在 LuoMethodInvocation 中了，允许修改实参
     */
    void setArguments(Object[] args);

    /**
     * 获得执行链中的目标方法
     * @return
     */
    Method getMethod();
}
