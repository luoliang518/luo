package com.luo.common.utils.ezPoiUtil.importAndExportAutomaticLogging;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ObjectClassProxy implements MethodInterceptor {

    private Object target;
    private String becauseOfValue;

    public ObjectClassProxy(Object target, String becauseOfValue) {
        this.target = target;
        this.becauseOfValue = becauseOfValue;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 在这里处理代理方法的调用逻辑
        Method[] methods = o.getClass().getMethods();
        List<Method> list = Arrays.asList(methods);
        if (!list.contains(method)) {
            return method.invoke(target, becauseOfValue);
        }
        // 其他情况直接调用原始对象的方法
        return method.invoke(target, objects);
    }
}
