package com.luo.common.enums;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @program: esign-tools
 * @description
 * @author: 罗亮
 * @create: 2022-11-14 16:02
 **/
public class EnumUtils {
    private static final String RESOURCE_PATTERN = "/**/*.class";
    private static final TypeFilter FILTER = new AssignableTypeFilter(BaseEnum.class);
    private static final ResourcePatternResolver RESOLVER =
            new PathMatchingResourcePatternResolver();

    private static Set<String> getAllPkgClassExtendErrorEnum(String pkg) throws IOException {
        Set<String> classNames = new TreeSet<>();
        String pattern =
                ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(pkg)
                        + RESOURCE_PATTERN;
        Resource[] resources = RESOLVER.getResources(pattern);
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(RESOLVER);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                String className = reader.getClassMetadata().getClassName();
                if (FILTER.match(reader, readerFactory)) {
                    classNames.add(className);
                }
            }
        }
        return classNames;
    }

    private static Set<String> getAllPkgClassExtendError(String pkg) throws IOException {
        Set<String> classNames = new TreeSet<>();
        String pattern =
                ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(pkg)
                        + RESOURCE_PATTERN;
        Resource[] resources = RESOLVER.getResources(pattern);
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(RESOLVER);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                String className = reader.getClassMetadata().getClassName();
                if (!className.contains("$")) {
                    classNames.add(className);
                }

            }
        }
        return classNames;
    }

    public static void checkErrorCode() throws Exception {
        // validation  错误信息
        // 获取类的Class对象
//        Set<String> clzzs1 =
//                getAllPkgClassExtendError("javax.validation.constraints");
//        Set<String> clzzs2 =
//                getAllPkgClassExtendError("cn.esign.ka.signs");
//        for (String clazz1 : clzzs1) {
//            Class clz1 = Class.forName(clazz1);
//            for (String clazz2 : clzzs2) {
//                if (!clazz2.equals("cn.esign.ka.signs.service.core.sign.PdfInnerSignContainer")
//                        && !clazz2.equals("cn.esign.ka.signs.service.core.sign.PdfOutSignContainer")) {
//                    Class<?> clz2 = Class.forName(clazz2);
//                    // 获取属性
//                    Field[] fields = clz2.getDeclaredFields();
//                    for (Field field : fields) {
//                        if (field.getAnnotations() != null) {
//                            // 获取属性上的注解
//                            Object fieldAnnotation = field.getAnnotation(clz1);
//                            // 获取注解上的值
//                            Method errorCodeM = clz1.getMethod("message");
//                            if (fieldAnnotation != null) {
//                                String fieldValue = errorCodeM.invoke(fieldAnnotation).toString();
//                                System.out.println("msg=" + fieldValue);
//                            }
//                        }
//                    }
//                }
//            }
//        }

        // 自定错误信息
        Map m = new HashMap();
        Set<String> clzzs =
                getAllPkgClassExtendErrorEnum("cn.esign.ka.tools.integrate.common.errorEnum");
        if (CollectionUtils.isEmpty(clzzs)) {
            return;
        }
        for (String clazz : clzzs) {
            Class<?> clz = Class.forName(clazz);
            Object[] objects = clz.getEnumConstants();
            Method status = clz.getMethod("status");
            Method value = clz.getMethod("value");
            for (Object obj : objects) {
                String status1 = status.invoke(obj).toString();
                String value1 = value.invoke(obj).toString();
//                System.out.println(value1);
                System.out.println(status1+"="+value1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        checkErrorCode();
    }
}
