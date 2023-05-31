package com.luo.spring.aop.advice.impl;

import com.luo.spring.aop.LuoAspectInstanceFactory;
import com.luo.spring.aop.advice.LuoAdvice;
import com.luo.spring.aop.advice.LuoMethodInterceptor;
import com.luo.spring.aop.advice.LuoMethodInvocation;
import com.luo.spring.aop.advice.LuoCommonAdvice;

import java.lang.reflect.Method;

public class LuoBeforeAdvice extends LuoCommonAdvice implements LuoAdvice, LuoMethodInterceptor {

    public LuoBeforeAdvice(Method aspectJAdviceMethod, LuoAspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    /*
    	public Object invoke(LuoMethodInvocation mi) throws Throwable {
		this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
		return mi.proceed();
	}
     */

    @Override
    public Object invoke(LuoMethodInvocation invocation) throws Throwable {
        before();
        return invocation.proceed();
    }

    public void before () throws Throwable {
        invokeAdviceMethod(null,null);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}