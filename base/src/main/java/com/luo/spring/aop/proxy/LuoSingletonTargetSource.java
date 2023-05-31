package com.luo.spring.aop.proxy;

public class LuoSingletonTargetSource implements LuoTargetSource {
    private final Object target;

    public LuoSingletonTargetSource(Object target) {
        this.target = target;
    }

    @Override
    public Object getTarget() throws Exception {
        return this.target;
    }
}
