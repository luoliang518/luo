package com.luo.spring.application;


import com.luo.spring.aop.LuoAnnotationAwareAspectJAutoProxyCreator;
import com.luo.spring.bean.LuoBeanDefinition;
import com.luo.spring.bean.LuoBeanNameAware;
import com.luo.spring.bean.LuoBeanPostProcessor;
import com.luo.spring.bean.LuoInitializingBean;
import com.luo.spring.component.LuoComponent;
import com.luo.spring.component.LuoComponentScan;
import com.luo.spring.component.LuoLazy;
import com.luo.spring.component.LuoScope;
import com.luo.spring.factory.LuoObjectFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LuoApplicationContext {
    private Class configClass;
    /**
     * 单例池 一级缓存
     */
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>() {
    };
    /**
     * 提前暴露的单例池  二级缓存
     */
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);
    /**
     * 三级缓存
     */
    private final Map<String, LuoObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>(16);
    /**
     * bean定义池
     */
    private Map<String, LuoBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, LuoBeanDefinition>() {
    };
    /**
     * bean后置处理器集合
     */
    private List<LuoBeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    /**
     * 正在创建的bean集合
     */
    private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));
    /**
     * 正在创建的原型bean集合
     */
    private final ThreadLocal<Object> prototypesCurrentlyInCreation = new ThreadLocal<>();


    public LuoApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        // 初始化beanDefinitionMap  bean定义池
        scanBeanDefinition(configClass);
        // 创建bean后置处理器 beanPostProcessor 放入beanPostProcessorList
        registerBeanPostProcessors();
    }

    private void registerBeanPostProcessors() {
        // 将常用bean注册到bean定义池 如AOP的bean定义
        registerCommonBeanPostProcessor();
        // 将所有的beanPostProcessor放入beanPostProcessorList
        beanDefinitionMap.forEach((beanName, luoBeanDefinition) -> {
            if (LuoBeanPostProcessor.class.isAssignableFrom(luoBeanDefinition.getClazz())) {
                // 获取到LuoBeanPostProcessor对象
                LuoBeanPostProcessor luoBeanPostProcessor = (LuoBeanPostProcessor) getBean(beanName);
                // 如果是bean后置处理器，就放入beanPostProcessorList
                beanPostProcessorList.add(luoBeanPostProcessor);
            }
        });
    }

    /**
     * 将bean定义放入bean定义池 如AOP的bean定义
     */
    private void registerCommonBeanPostProcessor() {
        LuoBeanDefinition luoBeanDefinition = new LuoBeanDefinition();
        luoBeanDefinition.setScope("singleton");
        luoBeanDefinition.setClazz(LuoAnnotationAwareAspectJAutoProxyCreator.class);
//        beanDefinitionMap.put("luoAnnotationAwareAspectJAutoProxyCreator", luoBeanDefinition);
        // 在spring中，这个beanName为internalAutoProxyCreator
        beanDefinitionMap.put("internalAutoProxyCreator", luoBeanDefinition);
    }

    /**
     * 扫描bean定义 然后再创建bean定义
     * 在创建bean时 要依赖注入  需要检测是否有某个类的依赖
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        // 1.先从单例池中获取
        Object o = singletonObjects.get(beanName);
        if (o != null) {
            return o;
        }
        // 2.获取beanDefinition
        LuoBeanDefinition luoBeanDefinition = beanDefinitionMap.get(beanName);
        if (luoBeanDefinition == null) {
            throw new NullPointerException();
        }
        // 3.判断是单例还是多例
        if (luoBeanDefinition.isSingleton()) {
            Object singleton = getSingleton(beanName, true);
            if (singleton != null) {
                return singleton;
            }
            // 若三级缓存都没有 则创建bean
            Object bean = createBean(beanName, luoBeanDefinition);
            // 将创建好的bean放入单例池 从二级缓存、三级缓存中移除
            this.singletonObjects.put(beanName, bean);
            this.earlySingletonObjects.remove(beanName);
            this.singletonFactories.remove(beanName);
            return bean;
        } else {
            // 多例
            return createBean(beanName, luoBeanDefinition);
        }
    }

    /**
     * 获取单例bean
     *
     * @param beanName
     * @param allowEarlyReference allowEarlyReference选项允许从早期引用缓存中获取引用，但并不意味着它可以一直设置为true。以下是一些原因：
     *                            循环引用问题：如果存在循环引用的情况，设置allowEarlyReference为true可能导致循环引用链无法正确解析。这可能导致无限递归或栈溢出错误。
     *                            初始化顺序问题：如果Bean之间存在复杂的依赖关系，并且它们的初始化顺序很重要，过早地从缓存中获取引用可能破坏正确的初始化顺序。这可能导致依赖关系未能正确建立，导致错误或不一致的状态。
     *                            容器管理问题：Spring容器负责管理Bean的生命周期和初始化过程。通过允许早期引用，可能会绕过某些容器提供的初始化控制和依赖解析机制，破坏了容器的一致性和管理能力。
     *                            性能问题：允许早期引用可能导致更多的查找和解析操作，这可能增加了系统的开销和复杂性，影响性能。
     * @return
     */
    private Object getSingleton(String beanName, boolean allowEarlyReference) {
        // 单例 尝试依次从3级缓存中获取
        // 3.1 先从一级缓存中获取 一级缓存中存放的是完整对象
        Object bean1 = singletonObjects.get(beanName);
        if (bean1 != null) {
            return bean1;
        }
        // 3.2 一级缓存中没有，从二级缓存中获取 二级缓存中存放的是早期对象 未被cglib增强
        Object bean2 = earlySingletonObjects.get(beanName);
        if (bean2 == null && allowEarlyReference) {
            // 3.3 二级缓存中没有，从三级缓存中获取 三级缓存中存放的是原始对象(早期引用) 已经被cglib增强 使用单例工厂池
            LuoObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
            if (singletonFactory != null) {
                Object bean3 = singletonFactory.getObject();
                // 将三级缓存中的对象放入二级缓存中
                earlySingletonObjects.put(beanName, bean3);
                // 从三级缓存中移除
                singletonFactories.remove(beanName);
                return bean3;
            }
        }
        return null;
    }

    /**
     * 创建bean
     *
     * @param beanName
     * @param luoBeanDefinition
     * @return
     */
    private Object createBean(String beanName, LuoBeanDefinition luoBeanDefinition) {
        // 创建bean前置处理 判断是否有循环依赖
        beforeCreation(beanName, luoBeanDefinition);
        try {
            // 创建bean对象
            Object instance = createBeanInstance(luoBeanDefinition);
            // 如果当前创建的是单例对象，依赖注入前将工厂对象
            // 在二级缓存没有时，再去获取这个工厂类对象，并将结果存于二级缓存也是可行的！但是：
            // 正常的代理的对象初始化后期调用生成的，是基于后置处理器PostProcessor的，
            // 若提早的代理就违背了Bean定义的生命周期。所以spring在一个三级缓存放置一个工厂，
            // 如果产生循环依赖 ，那么就会调用这个工厂提早的得到代理的对象
            if (luoBeanDefinition.isSingleton()) {
                // 将对象放入二级缓存中
                earlySingletonObjects.put(beanName, instance);
                // 将对象放入三级缓存中
                singletonFactories.put(beanName, new LuoObjectFactory<Object>() {
                    @Override
                    public Object getObject() throws RuntimeException {

                        return null;
                    }
                });
            }
//            for (Field declaredField : clazz.getDeclaredFields()) {
//                if (declaredField.isAnnotationPresent(LuoAutowired.class)) {
//                    // 获取属性名
//                    AtomicReference<String> fieldName = new AtomicReference<>(declaredField.getName());
//                    // 获取属性类型
//                    Class<?> type = declaredField.getType();
//                    beanDefinitionMap.forEach((a, b) -> {
//                        if (type.isAssignableFrom(b.getClazz())) {
//                            fieldName.set(a);
//                        }
//                    });
//                    // 获取依赖的Bean实例
//                    Object bean = getBean(fieldName.get());
//                    declaredField.setAccessible(true);
//                    // 反射注入依赖
//                    declaredField.set(instance, bean);
//                }
//            }
//            return instance;
            // aware回调
            if (instance instanceof LuoBeanNameAware) {
                ((LuoBeanNameAware) instance).setBeanName(beanName); // 设置Bean名称Aware
            }
            // BeanPostProcessor前置处理
            beanPostProcessorList.forEach(beanPostProcessor -> beanPostProcessor.postProcessBeforeInitialization(instance, beanName));
            // 初始化
            if (instance instanceof LuoInitializingBean) {
                ((LuoInitializingBean) instance).afterPropertiesSet();
            }
            // BeanPostProcessor后置处理
            beanPostProcessorList.forEach(beanPostProcessor -> beanPostProcessor.postProcessAfterInitialization(instance, beanName));
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object createBeanInstance(LuoBeanDefinition luoBeanDefinition) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class<?> clazz = luoBeanDefinition.getClazz();
        // 优先使用无参构造
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return constructor.newInstance();
            }
        }
        // 没有无参构造器  通过有参构造器注入
        // 获取所有构造器
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        // 获取有参构造器
        Constructor<?> constructor = Arrays.stream(declaredConstructors).filter(declaredConstructor -> declaredConstructor.getParameterCount() > 0).findFirst().get();
        // 获取有参构造器的参数
        Parameter[] parameters = constructor.getParameters();
        Object[] args = new Object[constructor.getParameterCount()];
        // 有参构造器参数注入
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object arg = null;
            if (parameter.getType().equals(LuoObjectFactory.class)) {
                // ObjectFactory 参数
                arg = (LuoObjectFactory<Object>) () -> getBean(parameter.getName());
            } else if (parameter.isAnnotationPresent(LuoLazy.class)) {
                // 参数加了 @Lazy，生成代理
                arg = buildLazyResolutionProxy(parameter.getName(), parameter.getType());
            } else {
                // 不是 ObjectFactory 也没加 @Lazy 的，直接从容器中拿
                arg = getBean(parameter.getName());
            }
            args[i] = arg;
        }
        return constructor.newInstance(args);
    }

    // todo
    private Object buildLazyResolutionProxy(String name, Class<?> type) {
        return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{type}, (proxy, method, args) -> {
            Object bean = getBean(name);
            return method.invoke(bean, args);
        });
    }

    private void beforeCreation(String beanName, LuoBeanDefinition luoBeanDefinition) {
        // 判断是否为单例
        if (luoBeanDefinition.isSingleton()) {
            // 判断是否正在创建
            if (singletonsCurrentlyInCreation.contains(beanName)) {
                throw new RuntimeException("创建bean:" + beanName + " 有循环依赖");
            }
            // 将bean放入正在创建的bean集合中
            singletonsCurrentlyInCreation.add(beanName);
        } else {
            // 原型bean
            Object prototypeBean = prototypesCurrentlyInCreation.get();
            if (prototypeBean != null && prototypeBean.equals(beanName)
                    || (prototypeBean instanceof Set && ((Set<?>) prototypeBean).contains(beanName))) {
                throw new RuntimeException("创建bean:" + beanName + " 有循环依赖");
            }
            // 将bean放入正在创建的bean集合中
            if (prototypeBean == null) {
                prototypesCurrentlyInCreation.set(beanName);
            } else if (prototypeBean instanceof String) {
                Set<String> beanNameSet = new HashSet<>();
                beanNameSet.add((String) prototypeBean);
                beanNameSet.add(beanName);
                prototypesCurrentlyInCreation.set(beanNameSet);
            } else {
                ((Set<String>) prototypeBean).add(beanName);
            }
        }
    }

    /**
     * 扫描bean 将bean定义放入bean定义池
     */
    private void scanBeanDefinition(Class<?> configClass) throws RuntimeException {
        ClassLoader classLoader = LuoApplicationContext.class.getClassLoader();
        // 获取到所有扫包路径下的类名
        List<String> classNames = getClassNames(configClass, classLoader);
        classNames.stream()
                .map(className -> {
                    try {
                        return classLoader.loadClass(className);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                // 表示为一个bean
                .filter(clazz -> clazz.isAnnotationPresent(LuoComponent.class))
                .forEach(clazz -> {
                    // 判断是否实现了接口LuoBeanPostProcessor
                    if (LuoBeanPostProcessor.class.isAssignableFrom(clazz)) {
                        try {
                            LuoBeanPostProcessor luoBeanPostProcessor = (LuoBeanPostProcessor) clazz.newInstance();
                            beanPostProcessorList.add(luoBeanPostProcessor);
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    LuoComponent declaredAnnotation = clazz.getDeclaredAnnotation(LuoComponent.class);
                    String beanName = declaredAnnotation.value();
                    if (beanDefinitionMap.containsKey(beanName)) {
                        throw new RuntimeException("beanName重复");
                    }
                    if (beanName == null || beanName.equals("")) {
                        // 获取到实现接口的类
                        Class<?>[] interfaces = clazz.getInterfaces();
                        if (interfaces.length == 1) {
                            beanName = interfaces[0].getSimpleName();
                            beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
                        } else {
                            beanName = clazz.getSimpleName();
                            beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
                        }
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

    private List<String> getClassNames(Class<?> configClass, ClassLoader classLoader) {
        List<String> classNames = new ArrayList<>();
        // 获取配置类上的LuoComponentScan注解
        LuoComponentScan componentScan = configClass.getDeclaredAnnotation(LuoComponentScan.class);
        String path = componentScan.value();
        if (path == null || path.isEmpty()) {
            // 如果注解未指定扫描路径，则使用配置类所在包的路径
            path = configClass.getPackage().getName();
        }
        // 根据路径获取资源URL
        URL resource = classLoader.getResource(path.replace(".", "/"));
        if (resource != null) {
            File file = new File(resource.getFile());
            // 递归获取文件中的类名
            getClassNamesByFile(file, classNames, file.toPath());
        }
        return classNames;
    }

    private void getClassNamesByFile(File file, List<String> classNames, Path basePath) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                // 递归遍历文件夹中的文件
                for (File f : files) {
                    getClassNamesByFile(f, classNames, basePath);
                }
            }
        } else if (file.getName().endsWith(".class")) {
            // 获取文件的绝对路径
            Path filePath = file.toPath();
            // 计算文件相对于基础路径的相对路径
            Path relativePath = basePath.relativize(filePath);
            // 将文件相对路径转换为类名
            String className = relativePath.toString().replace(File.separator, ".").replaceAll("\\.class$", "");
            // 添加到类名列表
            classNames.add(className);
        }
    }


}
