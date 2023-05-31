package com.luo.spring.aop.advice;

import com.luo.spring.bean.LuoOrdered;

/**
 * 切面
 */
public interface LuoAdvisor extends LuoPointcutAdvisor,LuoOrdered {
    LuoAdvice getAdvice();
}
