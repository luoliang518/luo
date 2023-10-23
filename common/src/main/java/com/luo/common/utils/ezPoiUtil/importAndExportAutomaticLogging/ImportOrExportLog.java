package com.luo.common.utils.ezPoiUtil.importAndExportAutomaticLogging;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author luoliang
 * @description 导入导出工具类
 * @date 2023/10/11 11:35
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface ImportOrExportLog {
    // 导入或导出
    ImportOrExportEnum type();

    // 映射类
    Class clazz();

}
