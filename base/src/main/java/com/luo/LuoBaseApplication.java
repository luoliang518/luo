package com.luo;

import com.luo.spring.UserBeanConfig;
import com.luo.spring.application.LuoApplicationContext;
import com.luo.spring.service.impl.UserBaseV2ServiceImpl;

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

        LuoApplicationContext luoApplicationContext = new LuoApplicationContext(UserBeanConfig.class);
        UserBaseV2ServiceImpl userBaseV2ServiceImpl = (UserBaseV2ServiceImpl) luoApplicationContext.getBean("userBaseV2ServiceImpl");
        System.out.println(userBaseV2ServiceImpl.sout());
    }
}