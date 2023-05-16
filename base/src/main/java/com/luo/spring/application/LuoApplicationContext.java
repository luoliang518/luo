package com.luo.spring.application;


import com.luo.spring.bean.LuoBeanDefinition;
import com.luo.spring.bean.LuoScope;
import com.luo.spring.component.LuoComponent;
import com.luo.spring.component.LuoComponentScan;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LuoApplicationContext {
    private Class configClass;
    /**
     * 单例池
     */
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>() {
    };
    /**
     * bean定义池
     */
    private Map<String, LuoBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, LuoBeanDefinition>() {
    };

    public LuoApplicationContext(Class configClass) {
        this.configClass = configClass;
        // 解析配置类
        // 初始化beanDefinitionMap  bean定义池
        initBeanDefinitionMap(configClass);
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    private void initBeanDefinitionMap(Class configClass) throws RuntimeException {
        // 解析配置类 componentScan
        LuoComponentScan componentScan = (LuoComponentScan) configClass.getDeclaredAnnotation(LuoComponentScan.class);
        // 获取扫描路径
        String path = componentScan.value();
        if (path == null || path.equals("")) {
            path = configClass.getPackage().getName();
        }
        ClassLoader classLoader = LuoApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(path.replace(".", "/"));
        File file = new File(resource.getFile());
        List<String> classNames = getFiles(file);
        classNames.stream()
                .map(className -> {
                    try {
                        return classLoader.loadClass(className);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(clazz -> clazz.isAnnotationPresent(LuoComponent.class))
                .forEach(clazz -> {
                    LuoComponent declaredAnnotation = clazz.getDeclaredAnnotation(LuoComponent.class);
                    String beanName = declaredAnnotation.value();
                    if (beanDefinitionMap.containsKey(beanName)) {
                        throw new RuntimeException("beanName重复");
                    }
                    if (beanName == null || beanName.equals("")) {
                        beanName = clazz.getSimpleName();
                        beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
                    }
                    LuoBeanDefinition luoBeanDefinition = new LuoBeanDefinition();
                    if (clazz.isAnnotationPresent(LuoScope.class)) {
                        LuoScope scope = clazz.getDeclaredAnnotation(LuoScope.class);
                        luoBeanDefinition.setScope(scope.value());
                    } else {
                        luoBeanDefinition.setScope("singleton");
                    }
                    luoBeanDefinition.setClazz(clazz);
                    beanDefinitionMap.put(beanName, luoBeanDefinition);
                });
    }

    public Object getBean(String beanName) {
        // 1.先从单例池中获取
        Object o = singletonObjects.get(beanName);
        if (o != null) {
            return o;
        }
        // 2.获取beanDefinition
        LuoBeanDefinition luoBeanDefinition = beanDefinitionMap.get(beanName);
        if (luoBeanDefinition == null) {
            throw new RuntimeException("bean不存在");
        }
        // 3.判断是单例还是多例
        if (luoBeanDefinition.getScope().equals("singleton")) {
            // 单例
            Object bean = createBean(beanName, luoBeanDefinition);
            singletonObjects.put(beanName, bean);
            return bean;
        } else {
            // 多例
            return createBean(beanName, luoBeanDefinition);
        }
    }

    private Object createBean(String beanName, LuoBeanDefinition luoBeanDefinition) {
        try {
            // 1.获取class对象
            Class clazz = luoBeanDefinition.getClazz();
            // 2.反射创建对象
            Object instance = clazz.newInstance();
            // 3.依赖注入
            // 3.1 获取所有的属性
            // 3.2 遍历属性，判断是否有@LuoAutowired注解
            // 3.3 如果有，获取属性类型，从单例池中获取
            // 3.4 如果没有，跳过
            // 3.5 反射注入
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private List<String> getFiles(File file) {
        List<String> classNames = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    classNames.addAll(getFiles(f));
                }
            }
        } else {
            // 获取文件绝对路径
            String absolutePath = file.getAbsolutePath();
            // 获取到相对路径
            String com = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class")).replace("\\", ".");
            classNames.add(com);
        }
        return classNames;
    }

}
