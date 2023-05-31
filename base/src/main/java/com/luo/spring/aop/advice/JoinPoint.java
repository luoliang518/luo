package com.luo.spring.aop.advice;

public interface JoinPoint {
    Object proceed() throws Throwable;
}
