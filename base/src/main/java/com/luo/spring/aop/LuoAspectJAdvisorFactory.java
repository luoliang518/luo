package com.luo.spring.aop;

import com.luo.spring.aop.advice.LuoAdvisor;

import java.util.List;

public interface LuoAspectJAdvisorFactory {
    /**
     * 是否是切面类 @Aspect
     * @param clazz
     * @return
     */
    boolean isAspect(Class<?> clazz);

    /**
     * 解析 @Aspect 切面类中的所有切面
     * @param clazz
     * @return
     */
    List<LuoAdvisor> getAdvisors(Class<?> clazz);
}
