package com.luo.auth.domain.userAggregate.entity;

import com.luo.common.base.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Data
public class Tenant {
    private String tenantId;
    private String tenantName;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String cert;
    private String certIssueDate;
    private String certExpiryDate;
    private String certStatus;
}
