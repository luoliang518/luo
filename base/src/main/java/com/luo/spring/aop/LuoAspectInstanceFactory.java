package com.luo.spring.aop;

/**
 * 提供调用切面方法的类
 */
public interface LuoAspectInstanceFactory {
    /**
     * 创建切面类的实例
     */
    Object getAspectInstance();
}