package com.luo.common.utils.springUtils;

import com.luo.common.enums.unifiedEnums.UnifiedServiceHandleEnum;
import com.luo.common.result.IntegrateException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

public class ApplicationContextUtils {
    private static ApplicationContext applicationContext;
    private static Environment environment;
    public static void setApplication(ApplicationContext applicationContext){
        if (ApplicationContextUtils.applicationContext == null) {
            ApplicationContextUtils.applicationContext = applicationContext;
            environment = applicationContext.getEnvironment();
        }
    }

    // 通过class获取bean
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
    // 通过name获取bean
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
    // 通过class和name返回指定的bean
    public static<T> T getBean(String name, Class<T> clazz){
        return applicationContext.getBean(name, clazz);
    }

    // 获取当前服务地址
    public static String getAddress() {
        String address = null;
        try {
            address=InetAddress.getLocalHost().getHostAddress()+":"+ environment.getProperty("server.port");
        }catch (Exception e) {
            IntegrateException.buildExternalEx(UnifiedServiceHandleEnum.GET_IP_ADDRESS_FAIL);
        }
        return address;
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public static void setEnvironment(Environment environment) {
        ApplicationContextUtils.environment = environment;
    }
}
