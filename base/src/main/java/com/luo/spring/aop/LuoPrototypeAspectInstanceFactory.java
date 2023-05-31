package com.luo.spring.aop;

public class LuoPrototypeAspectInstanceFactory implements LuoAspectInstanceFactory {
    private Class<?> clazz;

    public LuoPrototypeAspectInstanceFactory(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object getAspectInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
