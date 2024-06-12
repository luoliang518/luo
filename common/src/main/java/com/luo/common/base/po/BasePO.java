package com.luo.common.base.po;

import com.luo.common.context.UserContext;
import lombok.Data;

import java.time.LocalDateTime;
/**
    所有PO都应当继承该类
 */
@Data
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
    // 逻辑删除
    private byte isDeleted;
    public BasePO createPO() {
        this.createUser = UserContext.get().getId();
        this.updateUser = UserContext.get().getId();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.isDeleted = 0;
        return this;
    }

    public BasePO updatePO() {
        this.updateUser = UserContext.get().getId();
        this.updateTime = LocalDateTime.now();
        this.isDeleted = 0;
        return this;
    }
    public BasePO deletedPO() {
        this.updateUser = UserContext.get().getId();
        this.updateTime = LocalDateTime.now();
        this.isDeleted = 1;
        return this;
    }
}
