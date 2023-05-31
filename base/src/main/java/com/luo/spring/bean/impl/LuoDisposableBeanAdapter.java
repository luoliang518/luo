package com.luo.spring.bean.impl;

import com.luo.spring.bean.LuoBeanDefinition;
import com.luo.spring.bean.LuoDisposableBean;

/**
 * 适配器
 */
public class LuoDisposableBeanAdapter implements LuoDisposableBean {
    private Object bean;
    private String beanName;
    private LuoBeanDefinition beanDefinition;

    public LuoDisposableBeanAdapter(Object bean, String beanName, LuoBeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.beanDefinition = beanDefinition;
    }

    public static boolean hasDestroyMethod(Object bean, LuoBeanDefinition beanDefinition) {
        if (bean instanceof LuoDisposableBean || bean instanceof AutoCloseable) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {
        try {
            if (bean instanceof LuoDisposableBean) {
                ((LuoDisposableBean) bean).destroy();
            } else if (bean instanceof AutoCloseable) {
                ((AutoCloseable) bean).close();
            }
        } catch (Exception e) {
            System.out.println("Invocation of destroy method failed on bean with name '" + this.beanName + "'");
        }
    }
}
