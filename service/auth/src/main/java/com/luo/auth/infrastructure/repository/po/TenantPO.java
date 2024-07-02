package com.luo.auth.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luo.common.base.BasePO;
import com.luo.common.enums.CertStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tenant")
public class TenantPO extends BasePO {
    private String tenantName;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String cert;
    private String certIssueDate;
    private String certExpiryDate;
    private CertStatus certStatus;
}
