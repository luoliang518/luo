package com.luo.auth.domain.roleAggregate.entity;

import com.luo.common.enums.PermissionLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private Long permissionId;
    private Long permissionParId;
    private PermissionLevelEnum permissionLevel;
    private String permissionCode;
    private String permissionName;
    private String permissionDesc;
}
