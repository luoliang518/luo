package com.luo.common.utils.ezPoiUtil.entity;

import com.luo.common.utils.ezPoiUtil.EzFiledRo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author luoliang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportRedisRo implements Serializable {
    // 前端传入的导出字段
    private List<EzFiledRo> exportBeanFrom;
    // 数据列表
    private List<Object> dataList;
    // 失败数据列表
    private List<ExportFailedRo> failedList;
    // 文件名称
    private String fileName;
    // 表格名称
    private String sheetName;
    // 导出类
    private Class<Object> clazz;
    // 读出的excel数据
    private List<ImportDataDto<Object>> excelData;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ExportFailedRo implements Serializable {
        private Object failedData;
        private String errorMessage;
    }
}