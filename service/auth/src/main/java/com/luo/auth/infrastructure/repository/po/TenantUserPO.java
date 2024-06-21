package com.luo.auth.infrastructure.repository.po;

import com.luo.common.base.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TenantUserPO extends BasePO {
    private String tenantName;
    private Long userId;
    private String userName;
}
