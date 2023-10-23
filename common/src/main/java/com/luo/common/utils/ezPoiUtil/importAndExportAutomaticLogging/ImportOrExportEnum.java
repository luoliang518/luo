package com.luo.common.utils.ezPoiUtil.importAndExportAutomaticLogging;

/**
 * @author luoliang
 * @description
 * @date 2023/10/11 15:02
 */
public enum ImportOrExportEnum {
    IMPORT("导入"),
    EXPORT("导出");
    private final String name;

    ImportOrExportEnum(String name) {
        this.name = name;
    }
}
