package com.luo.common.utils.ezPoiUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoliang
 * @description 字段名与导出中文名映射基类
 * @date 2023/10/7 16:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EzFiledRo {
    private String englishFiledName;

    private String chineseTitleName;
}
