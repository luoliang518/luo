package com.luo.spring.aop.proxy.impl;

import com.luo.spring.aop.LuoAopContext;
import com.luo.spring.aop.advice.LuoInterceptor;
import com.luo.spring.aop.impl.DefaultMethodInvocation;
import com.luo.spring.aop.proxy.LuoAopProxy;
import com.luo.spring.aop.proxy.LuoProxyFactory;
import com.luo.spring.aop.proxy.LuoTargetSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class LuoJDKDynamicAopProxyImpl implements LuoAopProxy,InvocationHandler {
    private final Class<?>[] proxiedInterfaces;

    private LuoProxyFactory proxyFactory;

    public LuoJDKDynamicAopProxyImpl(LuoProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
        this.proxiedInterfaces = completeProxiedInterfaces(this.proxyFactory);
    }

    public LuoJDKDynamicAopProxyImpl(Class<?>[] proxiedInterfaces, LuoProxyFactory proxyFactory) {
        this.proxiedInterfaces = proxiedInterfaces;
        this.proxyFactory = proxyFactory;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                this.proxiedInterfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object oldProxy = null;
        boolean setProxyContext = false;
        LuoTargetSource targetSource = this.proxyFactory.getTargetSource();
        Object target = null;
        try {
            Object retVal;
            if (this.proxyFactory.exposeProxy()) {
                // 将当前正在运行的代理对象暴露给 AopContext中的本地线程变量
                oldProxy = LuoAopContext.setCurrentProxy(proxy);
                setProxyContext = true;
            }
            target = targetSource.getTarget();
            Class<?> targetClass = target.getClass();

            // 得到此 method 的拦截器链，就是一堆环绕通知
            // 需要根据 invoke 的 method 来做进一步确定，过滤出应用在这个 method 上的 Advice
             List<LuoInterceptor> chain = this.proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
             if (chain.isEmpty()) {
                  // 使用反射调用目标对象的方法（调用连接点方法）
                 retVal = method.invoke(target, args);
            } else {
                DefaultMethodInvocation methodInvocation = new DefaultMethodInvocation(target, method, args, chain);
                retVal = methodInvocation.proceed();
            }
            // 处理特殊的返回值 this
            Class<?> returnType = method.getReturnType();
            if (retVal != null && retVal == target &&
                    returnType != Object.class && returnType.isInstance(proxy)) {
                retVal = proxy;
            }
            return retVal;
        } finally {
            if (setProxyContext) {
                LuoAopContext.setCurrentProxy(oldProxy);
            }
        }
    }

    /**
     * TODO 补充代理对象的接口，如 SpringProxy、Advised、DecoratingProxy
     *
     * @param proxyFactory
     * @return
     */
    private Class<?>[] completeProxiedInterfaces(LuoProxyFactory proxyFactory) {
        Class<?>[] proxiedInterfaces = proxyFactory.getProxiedInterfaces();
        return proxiedInterfaces;
    }
}
