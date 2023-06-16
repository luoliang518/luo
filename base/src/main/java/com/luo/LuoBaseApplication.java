package com.luo;

import com.luo.spring.UserBeanConfig;
import com.luo.spring.application.LuoApplicationContext;
import com.luo.spring.test.service.UserBaseService;

public class LuoBaseApplication {
    public static void main(String[] args) throws ClassNotFoundException {
//            double[] X = {70, 85, 100, 115, 120, 135}; // 房屋面积
//            double[] y = {315, 398, 445, 470, 495, 560}; // 房价
//
//            SimpleRegression model = new SimpleRegression();
//
//            // 训练模型
//            for (int i = 0; i < X.length; i++) {
//                model.addData(X[i], y[i]);
//            }
//
//            // 进行预测
//            double X_new = 110; // 新的房屋面积
//            double y_pred = model.predict(X_new);
//
//            System.out.println("预测房价：" + y_pred);

        LuoApplicationContext applicationContext = new LuoApplicationContext(UserBeanConfig.class);

        System.out.println("=================beanNames=================");
        for (String beanName : applicationContext.getBeanNames()) {
            System.out.println(beanName);
        }
        System.out.println("===========================================");

        UserBaseService userService = (UserBaseService) applicationContext.getBean("userBaseServiceImpl");
//        System.out.println("userService: " + userService.getClass().getName());
        System.out.println(userService.sout());
//        userService.toString();


//        LuoApplicationContext luoApplicationContext = new LuoApplicationContext(UserBeanConfig.class);
//        Object obj = luoApplicationContext.getBean("userBaseV2ServiceImpl");
//        UserBaseV2Service userBaseV2ServiceImpl = (UserBaseV2Service) obj;
//        System.out.println(userBaseV2ServiceImpl.toString());
//        System.out.println(userBaseV2ServiceImpl.sout());
//        luoApplicationContext.close();
//        ClassLoader classLoader = LuoApplicationContext.class.getClassLoader();
//        Class<?> aClass = classLoader.loadClass("com.luo.spring.aop.LuoAnnotationAwareAspectJAutoProxyCreator");
//        System.out.println(aClass);
    }
}