package com.luo.common.utils.ezPoiUtil.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author luoliang
 * @description 导入对象
 * @date 2023/10/12 10:09
 */
@Data
@AllArgsConstructor
@Builder
public class ImportDataDto<T> {
    private List<T> excelData;
    private String sheetName;
    private T sheetTitle;
    private T sheetExample;

    public ImportDataDto() {
    }
}
