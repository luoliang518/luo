package com.luo.auth.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luo.common.base.BasePO;
import com.luo.common.enums.PermissionLevelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role_permission")
public class RolePermissionPO extends BasePO {
    private Long roleId;
    private String roleName;
    private Long permissionId;
    private String permissionName;
}
