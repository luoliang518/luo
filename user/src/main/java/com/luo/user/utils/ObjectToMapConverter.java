package com.luo.user.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMapConverter {
    public static Map<String, Object> convert(Object object) {
        Map<String, Object> map = new HashMap<>();
        
        // 获取对象的 Class
        Class<?> clazz = object.getClass();
        
        // 获取对象的所有字段（包括继承的字段）
        Field[] fields = clazz.getDeclaredFields();
        
        // 遍历字段，并将字段名和字段值存储到 Map 中
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                map.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        return map;
    }
}
