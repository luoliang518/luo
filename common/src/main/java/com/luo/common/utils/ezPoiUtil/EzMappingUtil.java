package com.luo.common.utils.ezPoiUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author luoliang
 * @description 字段工具类
 * @date 2023/10/8 10:34
 */
public class EzMappingUtil {
    public static List<EzFiledRo> filter(Object exportBeanFrom) {
        List<EzFiledRo> list = new ArrayList<>();
        if (exportBeanFrom instanceof List) {
            List exportList = ((List<?>) exportBeanFrom);
            for (Object o : exportList) {
                EzFiledRo ezFiledRo = new EzFiledRo();
                Class<?> clazz = o.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        if (field.getName().equals("englishFiledName")) {
                            ezFiledRo.setEnglishFiledName((String) field.get(o));
                        } else {
                            ezFiledRo.setChineseTitleName((String) field.get(o));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                list.add(ezFiledRo);
            }
        }
        return list;
    }

    public static String[] getExportTitleAndSortArr(List<EzFiledRo> beanExportFieldMap, Class<?> clazz) {
        Collections.sort(
                beanExportFieldMap, Comparator.comparingInt(ro ->
                        getFieldSort(ro.getEnglishFiledName(), clazz)));
        String[] titles = beanExportFieldMap.stream()
                .map(EzFiledRo::getChineseTitleName)
                .toArray(String[]::new);
        return titles;
    }

    public static String[] getExportFieldAndSortArr(List<EzFiledRo> beanExportFieldMap, Class<?> clazz) {
        Collections.sort(beanExportFieldMap, Comparator.comparingInt(ro -> getFieldSort(ro.getEnglishFiledName(), clazz)));
        String[] values = beanExportFieldMap.stream()
                .map(EzFiledRo::getEnglishFiledName)
                .toArray(String[]::new);
        return values;
    }

    private static int getFieldSort(String fieldName, Class<?> clazz) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            EzFiled ezFiled = field.getAnnotation(EzFiled.class);
            if (ezFiled != null) {
                return ezFiled.sort();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
