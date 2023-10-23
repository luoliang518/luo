package com.luo.common.utils.ezPoiUtil.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luo.common.utils.ezPoiUtil.EzFiledRo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author luoliang
 * @description
 * @date 2023/10/17 9:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportErrorResponseVo {
    private IPage objectList;
    private List<EzFiledRo> ezFiledRos;
}
