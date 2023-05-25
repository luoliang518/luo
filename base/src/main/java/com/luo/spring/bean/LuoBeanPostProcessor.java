package com.luo.spring.bean;

public interface LuoBeanPostProcessor {
    /**
     * 类初始化之前调用
     * @param bean
     * @param beanName
     * @return
     */
    Object postProcessBeforeInitialization(Object bean, String beanName);

    /**
     * 类初始化之后调用
     * @param bean
     * @param beanName
     * @return
     */
    Object postProcessAfterInitialization(Object bean, String beanName);
}
