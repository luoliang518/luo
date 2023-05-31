package com.luo.spring.aop.impl;

import com.luo.spring.aop.advice.LuoMethodInterceptor;
import com.luo.spring.aop.advice.LuoMethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

public class DefaultMethodInvocation implements LuoMethodInvocation {
    private Object target;
    private Method method;
    private Object[] args;
    List<?> methodInterceptorList;
    private int currentInterceptorIndex = -1;

    public DefaultMethodInvocation(Object target, Method method, Object[] args, List<?> methodInterceptorList) {
        this.target = target;
        this.method = method;
        this.args = args;
        this.methodInterceptorList = methodInterceptorList;
    }

    @Override
    public Object proceed() throws Throwable {
        // 调用目标， 返回并结束递归
        if (this.currentInterceptorIndex == this.methodInterceptorList.size() - 1) {
            return invokeJoinpoint();
        }
        // 逐一调用通知, currentInterceptorIndex + 1
        Object methodInterceptor = this.methodInterceptorList.get(++currentInterceptorIndex);
        return ((LuoMethodInterceptor) methodInterceptor).invoke(this);
    }

    protected Object invokeJoinpoint() throws Throwable {
        return method.invoke(target, args);
    }
    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public void setArguments(Object[] args) {

    }

    @Override
    public Method getMethod() {
        return null;
    }
}
