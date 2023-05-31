package com.luo.spring.aop.proxy;

import com.luo.spring.aop.advice.*;
import com.luo.spring.aop.proxy.impl.LuoJDKDynamicAopProxyImpl;
import com.luo.spring.aop.proxy.impl.ObjenesisCglibAopProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LuoProxyFactory {

    private List<LuoAdvisor> advisorList;
    private LuoTargetSource luoTargetSource;
    private List<Class<?>> interfaces;
    private boolean proxyTargetClass;
    private static final Class<?>[] EMPTY_CLASS_ARRAY = {};

    /**
     * 是否允许代理对象作为 ThreadLocal 通过 AopContext 访问
     */
    private boolean exposeProxy = true;

    public LuoProxyFactory() {
        this.proxyTargetClass = false;
        this.advisorList = new ArrayList<>();
        this.interfaces = new ArrayList<>();
    }

    public void addAdvisors(List<LuoAdvisor> advisorList) {
        this.advisorList.addAll(advisorList);
    }

    public Object getProxy() {
        LuoAopProxy aopProxy = createAopProxy();
        return aopProxy.getProxy();
    }

    /**
     * 根据配置创建不同的 AopProxy
     * @return
     */

    public LuoAopProxy createAopProxy() {
        if (isProxyTargetClass()) {
            return new ObjenesisCglibAopProxy(this);
        } else {
            // 有接口
            if (!this.interfaces.isEmpty()) {
                return new LuoJDKDynamicAopProxyImpl(this);
            } else {
                // 没接口
                return new ObjenesisCglibAopProxy(this);
            }
        }
    }

    /**
     * TODO 没有实现动态通知调用
     * 得到此 method 的拦截器链，就是一堆环绕通知
     * 需要根据 invoke 的 method 来做进一步确定，过滤出应用在这个 method 上的 Advice
     *
     * @param method
     * @param targetClass
     * @return
     */
    public List<LuoInterceptor> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        List<LuoInterceptor> interceptorList = new ArrayList<>(this.advisorList.size());
        for (LuoAdvisor advisor : this.advisorList) {
            LuoMethodMatcher methodMatcher = advisor.getPointcut().getMethodMatcher();
            // 切点表达式匹配才添加此 MethodInterceptor
            if (methodMatcher.matches(method, targetClass)) {
                LuoAdvice advice = advisor.getAdvice();
                if (advice instanceof LuoMethodInterceptor) {
                    interceptorList.add((LuoMethodInterceptor) advice);
                }
            }
        }
        return interceptorList;
    }

    public void setTarget(Object bean) {
        setTargetSource(new LuoSingletonTargetSource(bean));
    }

    public void setTargetSource(LuoTargetSource luoTargetSource) {
        this.luoTargetSource = luoTargetSource;
    }

    public LuoTargetSource getTargetSource() {
        return luoTargetSource;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void addInterface(Class<?> intf) {
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
        }
        // 避免重复添加相同的接口
        if (!this.interfaces.contains(intf)) {
            this.interfaces.add(intf);
        }
    }

    public void setInterfaces(Class<?>... interfaces) {
        this.interfaces.clear();
        for (Class<?> intf : interfaces) {
            addInterface(intf);
        }
    }

    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(EMPTY_CLASS_ARRAY);
    }

    public boolean exposeProxy() {
        return exposeProxy;
    }

    public void setExposeProxy(boolean exposeProxy) {
        this.exposeProxy = exposeProxy;
    }
}
