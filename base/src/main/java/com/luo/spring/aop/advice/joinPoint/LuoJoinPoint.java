package com.luo.spring.aop.advice.joinPoint;

public interface LuoJoinPoint {

    /**
     * 获取执行链中目标方法的实参
     * @return
     */
    Object[] getArgs();

    /**
     * 获取执行链中目标方法的方法名
     * @return
     */
    String getMethodName();

}