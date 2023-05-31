package com.luo.spring.aop.impl;

import com.luo.spring.aop.advice.LuoAdvice;
import com.luo.spring.aop.advice.LuoAdvisor;
import com.luo.spring.aop.advice.LuoPointcut;

public class DefaultPointcutAdvisor implements LuoAdvisor {

    private LuoPointcut pointcut;
    private LuoAdvice advice;

    public DefaultPointcutAdvisor(LuoPointcut pointcut, LuoAdvice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public LuoPointcut getPointcut() {
        return pointcut;
    }

    @Override
    public LuoAdvice getAdvice() {
        return advice;
    }

    @Override
    public int getOrder() {
        return this.advice.getOrder();
    }
}