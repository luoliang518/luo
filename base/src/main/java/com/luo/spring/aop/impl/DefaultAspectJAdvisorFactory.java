package com.luo.spring.aop.impl;

import com.luo.spring.aop.LuoAspectJAdvisorFactory;
import com.luo.spring.aop.LuoPrototypeAspectInstanceFactory;
import com.luo.spring.aop.advice.LuoAdvisor;
import com.luo.spring.aop.advice.impl.LuoAfterAdvice;
import com.luo.spring.aop.advice.impl.LuoBeforeAdvice;
import com.luo.spring.aop.anno.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DefaultAspectJAdvisorFactory implements LuoAspectJAdvisorFactory {
    @Override
    public boolean isAspect(Class<?> clazz) {
        return clazz.isAnnotationPresent(LuoAspect.class);
    }

    @Override
    public List<LuoAdvisor> getAdvisors(Class<?> clazz) {
        LuoPrototypeAspectInstanceFactory aspectInstanceFactory = new LuoPrototypeAspectInstanceFactory(clazz);
        // 高级切面转低级切面类
        ArrayList<LuoAdvisor> list = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(LuoBefore.class)){
                // 切点 pointcut
                String expression = method.getAnnotation(LuoBefore.class).value();
                LuoAspectJExpressionPointcut pointcut = new LuoAspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知 （最终都是环绕通知）
                LuoBeforeAdvice advice = new LuoBeforeAdvice(method, aspectInstanceFactory);
                // 切面
                LuoAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            } else if (method.isAnnotationPresent(LuoAfter.class)){
                // 切点 pointcut
                String expression = method.getAnnotation(LuoAfter.class).value();
                LuoAspectJExpressionPointcut pointcut = new LuoAspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知 （最终都是环绕通知）
                LuoAfterAdvice advice = new LuoAfterAdvice(method, aspectInstanceFactory);
                // 切面
                LuoAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            } else if (method.isAnnotationPresent(LuoAfterReturning.class)){

            } else if (method.isAnnotationPresent(LuoAfterThrowing.class)){

            } else if (method.isAnnotationPresent(LuoAround.class)){

            }
        }
        return list;
    }
}
