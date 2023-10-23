package com.luo.common.utils.ezPoiUtil.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author luoliang
 * @description
 * @date 2023/10/11 9:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportBaseRo {
    //    @ApiModelProperty("是否覆盖")
    private Boolean whetherToCover;
    //    @ApiModelProperty("是否跳过错误")
    private Boolean whetherToSkipErrors;
    //    @ApiModelProperty("文件流集合")
    private List<MultipartFile> multipartFileList;
}
