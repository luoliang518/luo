package com.luo.spring.aop.advice;

public interface LuoMethodInterceptor  extends LuoInterceptor {
    Object invoke(LuoMethodInvocation invocation) throws Throwable;
}
