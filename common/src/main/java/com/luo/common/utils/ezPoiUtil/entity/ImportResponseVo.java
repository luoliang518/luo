package com.luo.common.utils.ezPoiUtil.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoliang
 * @description
 * @date 2023/10/16 18:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportResponseVo {
    private String fileName;
    private String msg;
    private String status;
    private int total;
    private int failedNum = 0;

    public static ImportResponseVo getImportMsg(String fileName, String status, int total, int failedNum) {
        return ImportResponseVo.builder()
                .fileName(fileName)
                .msg("共计导入" + total + "条,成功导入" + (total - failedNum) + "条" +
                        (failedNum == 0 ? "" : "异常数据" + failedNum + "条"))
                .status(status)
                .total(total)
                .failedNum(failedNum)
                .build();
    }
}
