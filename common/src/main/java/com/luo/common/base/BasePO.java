package com.luo.common.base;

import lombok.Data;

import java.time.LocalDateTime;
/**
    所有PO都应当继承该类
 */
@Data
public class BasePO {
    // 唯一标识
    private Long id;
    // 租户唯一标识 建表需增加索引
    private Long tenantId;
    // 创建人
    private Long createUser;
    // 更新人
    private Long updateUser;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除 建表需增加索引
    private byte deleted;
}
