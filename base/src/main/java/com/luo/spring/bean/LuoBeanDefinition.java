package com.luo.spring.bean;

public class LuoBeanDefinition {
    private Class clazz;
    private String scope;
    public boolean isSingleton() {
        return "singleton".equals(scope);
    }
    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public LuoBeanDefinition() {
    }

    public LuoBeanDefinition(Class clazz, String scope) {
        this.clazz = clazz;
        this.scope = scope;
    }
}
