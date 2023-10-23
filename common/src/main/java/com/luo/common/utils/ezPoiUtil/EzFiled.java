package com.luo.common.utils.ezPoiUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author luoliang
 * @description
 * @date 2023/10/7 16:04
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface EzFiled {
    int sort() default 0; // 从0开始依次递增 规定！！！！

    String name() default "";

    String example() default "";
}
