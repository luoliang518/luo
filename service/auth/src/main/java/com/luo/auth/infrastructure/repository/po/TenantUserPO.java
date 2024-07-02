package com.luo.auth.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luo.common.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tenant_user")
public class TenantUserPO extends BasePO {
    private String tenantName;
    private Long userId;
    private String userName;
}
