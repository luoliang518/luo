package com.luo.auth.domain.userAggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tenant {
    private Long tenantId;
    private String tenantName;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String cert;
    private String certIssueDate;
    private String certExpiryDate;
    private String certStatus;
}
