package com.luo.spring.aop.advice;

import java.lang.reflect.Method;

/**
 * 方法匹配器
 */
public interface LuoMethodMatcher {
    /**
     * 判断是否匹配
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);
}
