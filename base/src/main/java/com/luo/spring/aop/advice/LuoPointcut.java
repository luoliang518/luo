package com.luo.spring.aop.advice;

/**
 * 切点
 */
public interface LuoPointcut {
    /**
     * 使用一个 MethodMatcher 对象来判断某个方法是否有资格用于切面
     * @return
     */
    LuoMethodMatcher getMethodMatcher();
}
