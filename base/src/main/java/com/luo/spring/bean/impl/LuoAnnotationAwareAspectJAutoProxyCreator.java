package com.luo.spring.bean.impl;

import com.luo.spring.aop.LuoAspectJAdvisorFactory;
import com.luo.spring.aop.proxy.LuoProxyFactory;
import com.luo.spring.aop.proxy.LuoSingletonTargetSource;
import com.luo.spring.aop.advice.LuoAdvice;
import com.luo.spring.aop.advice.LuoAdvisor;
import com.luo.spring.aop.advice.LuoMethodMatcher;
import com.luo.spring.aop.advice.LuoPointcut;
import com.luo.spring.aop.impl.DefaultAspectJAdvisorFactory;
import com.luo.spring.application.LuoApplicationContext;
import com.luo.spring.application.LuoApplicationContextAware;
import com.luo.spring.bean.LuoOrderComparator;
import com.luo.spring.bean.LuoSmartInstantiationAwareBeanPostProcessor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LuoAnnotationAwareAspectJAutoProxyCreator implements LuoSmartInstantiationAwareBeanPostProcessor, LuoApplicationContextAware {
    private LuoApplicationContext context;
    private final LuoAspectJAdvisorFactory advisorFactory = new DefaultAspectJAdvisorFactory();
    private List<LuoAdvisor> cachedAdvisors;
    /**
     * 记录哪些 bean 尝试过提前创建代理，无论这个 bean 是否创建了代理增强，都记录下来，
     * 存的值就是 beanName
     */
    private final Set<Object> earlyProxyReferences = new HashSet<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return LuoSmartInstantiationAwareBeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean != null) {
            // earlyProxyReferences 中不包含当前 beanName，才创建代理
            if (!this.earlyProxyReferences.contains(beanName)) {
                return wrapIfNecessary(bean, beanName);
            } else {
                // earlyProxyReferences 中包含当前 beanName，不再重复进行代理创建，直接返回
                this.earlyProxyReferences.remove(beanName);
            }
        }
        return bean;
    }
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws RuntimeException {
        this.earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    @Override
    public void setLuoApplicationContext(LuoApplicationContext luoApplicationContext) {
        context=luoApplicationContext;
    }

    private Object wrapIfNecessary(Object bean, String beanName) {
        // 判断是否是切面基础设施类，如果是，直接返回
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        List<LuoAdvisor> advisorList = findEligibleAdvisors(bean.getClass(), beanName);
        if (!advisorList.isEmpty()) {
            // 创建代理
            Object proxy = createProxy(bean.getClass(), bean, beanName, advisorList);
            return proxy;
        }
        System.out.println("………………………………………………Did not to auto-proxy user class [" + bean.getClass().getName() + "],  beanName[" + beanName + "]");
        return bean;
    }

    private Object createProxy(Class<?> targetClass, Object target, String beanName, List<LuoAdvisor> advisorList) {
        LuoProxyFactory proxyFactory = new LuoProxyFactory();
        proxyFactory.setTargetSource(new LuoSingletonTargetSource(target));
        proxyFactory.addAdvisors(advisorList);
        proxyFactory.setInterfaces(targetClass.getInterfaces());
        System.out.println("给 " + beanName + " 创建代理，有 " + advisorList.size() + " 个切面。");
        return proxyFactory.getProxy();
    }

    private List<LuoAdvisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
        List<LuoAdvisor> candidateAdvisors = findCandidateAdvisors();
        List<LuoAdvisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);
        // 如果最终的 Advisor 列表不为空，再在开头位置添加一个 ExposeInvocationInterceptor
        // extendAdvisors(eligibleAdvisors);
        if (!eligibleAdvisors.isEmpty()) {
            LuoOrderComparator.sort(eligibleAdvisors);
        }
        return eligibleAdvisors;
    }

    private List<LuoAdvisor> findAdvisorsThatCanApply(List<LuoAdvisor> candidateAdvisors, Class<?> beanClass, String beanName) {
        if (candidateAdvisors.isEmpty()) {
            return candidateAdvisors;
        }
        List<LuoAdvisor> eligibleAdvisors = new ArrayList<>(candidateAdvisors.size());
        Method[] methods = beanClass.getDeclaredMethods();
        // 遍历 bean 目标类型的所有方法，包括继承来的接口方法等
        // 继承的方法没写
        for (LuoAdvisor advisor : candidateAdvisors) {
            LuoMethodMatcher methodMatcher = advisor.getPointcut().getMethodMatcher();
            for (Method method : methods) {
                if (methodMatcher.matches(method, beanClass)) {
                    eligibleAdvisors.add(advisor);
                    break;
                }
            }
        }
        return eligibleAdvisors;
    }

    private List<LuoAdvisor> findCandidateAdvisors() {
        List<LuoAdvisor> advisors = findCandidateAdvisorsInBeanFactory();
        advisors.addAll(findCandidateAdvisorsInAspect());
        return advisors;
    }

    /**
     * bean定义池 中所有 bean，找到被 @Aspect 注解标注的 bean，再去 @Aspect 类中封装 Advisor
     *
     * @return
     */
    private List<LuoAdvisor> findCandidateAdvisorsInAspect() {
        if (this.cachedAdvisors != null) {
            return this.cachedAdvisors;
        }
        List<Class<?>> allClass = context.getAllBeanClass();
        List<LuoAdvisor> advisors = new ArrayList<>();

        for (Class<?> cls : allClass) {
            if (this.advisorFactory.isAspect(cls)) {
                List<LuoAdvisor> classAdvisors = this.advisorFactory.getAdvisors(cls);
                advisors.addAll(classAdvisors);
            }
        }
        this.cachedAdvisors = advisors;
        return this.cachedAdvisors;
    }

    /**
     * 去容器中拿所有低级 Advisor
     *
     * @return
     */
    private List<LuoAdvisor> findCandidateAdvisorsInBeanFactory() {
        return new ArrayList<>();
    }

    /**
     * 判断是否是切面基础设施类
     * @param beanClass
     * @return
     */
    protected boolean isInfrastructureClass(Class<?> beanClass) {
        boolean retVal = LuoAdvice.class.isAssignableFrom(beanClass) ||
                LuoPointcut.class.isAssignableFrom(beanClass) ||
                LuoAdvisor.class.isAssignableFrom(beanClass) ||
                this.advisorFactory.isAspect(beanClass);
        if (retVal) {
            // logger.trace("Did not attempt to auto-proxy infrastructure class [" + beanClass.getName() + "]");
            System.out.println("………………………………………………Did not attempt to auto-proxy infrastructure class [" + beanClass.getName() + "]");
        }
        return retVal;
    }
}
