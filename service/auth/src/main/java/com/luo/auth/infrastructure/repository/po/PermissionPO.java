package com.luo.auth.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luo.common.base.po.BasePO;
import com.luo.common.enums.PermissionLevelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("permission")
public class PermissionPO extends BasePO {
    private Long roleId;
    private Long permissionParId;
    private PermissionLevelEnum permissionLevel;
    private String permissionCode;
    private String permissionName;
    private String permissionDesc;
}
