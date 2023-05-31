package com.luo.spring.bean;

public interface LuoSmartInstantiationAwareBeanPostProcessor extends LuoBeanPostProcessor{
    /**
     * 如果 bean 需要被代理，返回代理对象；不需要被代理直接返回原始对象。
     * @param bean
     * @param beanName
     * @return
     * @throws RuntimeException
     */
    default Object getEarlyBeanReference(Object bean, String beanName) throws RuntimeException {
        return bean;
    }
}
