package com.luo.spring.bean.impl;

import com.luo.spring.application.LuoApplicationContext;
import com.luo.spring.application.LuoApplicationContextAware;
import com.luo.spring.bean.LuoBeanPostProcessor;
import com.luo.spring.component.LuoComponent;
import com.luo.spring.service.UserBaseV2Service;
import com.luo.spring.service.impl.UserBaseV2ServiceImpl;

import java.lang.reflect.Proxy;

@LuoComponent
public class LuoBeanPostProcessorImpl implements LuoBeanPostProcessor, LuoApplicationContextAware {

    private LuoApplicationContext context;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof UserBaseV2Service) {
            ((UserBaseV2ServiceImpl)bean).setName("UserBaseV2ServiceImpl postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof UserBaseV2Service) {
            // 对UserBaseV2Service进行代理
            Object userBaseV2ServiceProxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{UserBaseV2Service.class}, (proxy, method, args) -> {
                System.out.println("UserBaseV2Service 代理");
                return method.invoke(bean, args);
            });
            return userBaseV2ServiceProxy;
        }


        return bean;
    }

    @Override
    public void setLuoApplicationContext(LuoApplicationContext LuoApplicationContext){
        this.context = LuoApplicationContext;
    }
}
