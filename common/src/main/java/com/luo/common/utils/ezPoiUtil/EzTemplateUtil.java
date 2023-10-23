package com.luo.common.utils.ezPoiUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoliang
 * @description 导出模板工具类
 * @date 2023/10/11 14:07
 */
public class EzTemplateUtil {
    public static <T> List<Object> getTemplateExample(Class<T> objectClass) {
        List<Object> objects = new ArrayList<>();
        Field[] declaredFields = objectClass.getDeclaredFields();
        T objectInstance = null;
        try {
            objectInstance = objectClass.newInstance();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                EzFiled dynamicExportAnnotation = declaredField.getAnnotation(EzFiled.class);
                String example = dynamicExportAnnotation.example();
                String fieldName = declaredField.getName();
                String setterMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method setterMethod = objectClass.getMethod(setterMethodName, String.class);
                setterMethod.invoke(objectInstance, example);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        objects.add(objectInstance);
        return objects;
    }
}
