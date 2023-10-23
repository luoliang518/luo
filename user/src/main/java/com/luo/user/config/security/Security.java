package com.luo.user.config.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luoliang
 * @description 权限接口校验注解
 * @date 2023/10/23 14:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Security {
    long id() default 0; // 例如 1001 10为类前缀 01为接口前缀 为了方便映射管理

    String name(); // 接口名称
}
