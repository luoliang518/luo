package com.luo.auth.application.user.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantVo implements Serializable {
    private Long tenantId;
    private String tenantName;
}
