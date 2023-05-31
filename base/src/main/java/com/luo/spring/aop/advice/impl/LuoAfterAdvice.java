package com.luo.spring.aop.advice.impl;

import com.luo.spring.aop.LuoAspectInstanceFactory;
import com.luo.spring.aop.advice.LuoAdvice;
import com.luo.spring.aop.advice.LuoCommonAdvice;
import com.luo.spring.aop.advice.LuoMethodInterceptor;
import com.luo.spring.aop.advice.LuoMethodInvocation;

import java.lang.reflect.Method;

public class LuoAfterAdvice extends LuoCommonAdvice implements LuoAdvice, LuoMethodInterceptor {
    public LuoAfterAdvice(Method aspectJAdviceMethod, LuoAspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    @Override
    public Object invoke(LuoMethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } finally {
            after();
        }
    }

    private void after() throws Throwable {
        invokeAdviceMethod(null, null);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
