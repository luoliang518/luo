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
public class TenantPO extends BasePO {
    private String tenantName;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String cert;
    private String certIssueDate;
    private String certExpiryDate;
    private String certStatus;
}
