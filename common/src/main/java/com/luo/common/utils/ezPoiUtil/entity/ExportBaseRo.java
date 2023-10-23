package com.luo.common.utils.ezPoiUtil.entity;

import com.luo.common.utils.ezPoiUtil.EzFiledRo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author luoliang
 * @description
 * @date 2023/10/9 16:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportBaseRo {
    private String fileName;
    private List<EzFiledRo> ezFiledRos;
}
