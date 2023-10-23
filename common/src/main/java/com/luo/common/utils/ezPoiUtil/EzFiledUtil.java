package com.luo.common.utils.ezPoiUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoliang
 * @description 获取映射类字段名称与中文名
 * @date 2023/10/8 10:57
 */
public class EzFiledUtil {
    public static <T> List<EzFiledRo> exportPageListDynamicFieldList(Class<T> objectClass) {
        Field[] declaredFields = objectClass.getDeclaredFields();
        List<EzFiledRo> dynamicFieldList = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            EzFiled dynamicExportAnnotation = declaredField.getAnnotation(EzFiled.class);
            String chineseTitle = dynamicExportAnnotation.name();
            dynamicFieldList.add(EzFiledRo.builder().englishFiledName(declaredField.getName()).chineseTitleName(chineseTitle).build());
        }
        return dynamicFieldList;
    }

}
