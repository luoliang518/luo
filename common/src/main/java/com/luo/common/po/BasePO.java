package com.luo.common.po;

import java.time.LocalDateTime;
/*
    所有PO都应当继承该类
 */
public class BasePO {
    // 唯一标识
    private Long id;
    // 创建人
    private String createUser;
    // 更新人
    private String updateUser;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
}
