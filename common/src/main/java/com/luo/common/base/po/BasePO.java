package com.luo.common.base.po;

import com.luo.common.context.user.UserContextHolder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
/**
    所有PO都应当继承该类
 */
@Data
public class BasePO {
    // 唯一标识
    private Long id;
    // 租户唯一标识
    private Long tenantId;
    // 创建人
    private Long createUser;
    // 更新人
    private Long updateUser;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除
    private byte deleted;
    public BasePO createPO() {
        this.createUser = UserContextHolder.get().getUserId();
        this.updateUser = UserContextHolder.get().getUserId();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.deleted = 0;
        return this;
    }

    public BasePO updatePO() {
        this.updateUser = UserContextHolder.get().getUserId();
        this.updateTime = LocalDateTime.now();
        this.deleted = 0;
        return this;
    }
    public BasePO deletedPO() {
        this.updateUser = UserContextHolder.get().getUserId();
        this.updateTime = LocalDateTime.now();
        this.deleted = 1;
        return this;
    }
}
