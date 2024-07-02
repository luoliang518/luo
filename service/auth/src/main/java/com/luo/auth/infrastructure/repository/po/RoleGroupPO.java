package com.luo.auth.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luo.common.base.BasePO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role_group")
@Builder
public class RoleGroupPO extends BasePO {
    private String roelGroupCode;
    private String roleGroupName;
    private String roleIds;
}
