package com.luo.spring.aop.proxy.impl;

import com.luo.spring.aop.proxy.LuoAopProxy;
import com.luo.spring.aop.proxy.LuoProxyFactory;

public class ObjenesisCglibAopProxy implements LuoAopProxy {

    private LuoProxyFactory proxyFactory;

    public ObjenesisCglibAopProxy(LuoProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    @Override
    public Object getProxy() {
        return null;
    }
}
