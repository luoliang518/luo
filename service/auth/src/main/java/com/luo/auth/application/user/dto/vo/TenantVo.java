package com.luo.auth.application.user.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantVo {
    private String tenantId;
    private String tenantName;
}
