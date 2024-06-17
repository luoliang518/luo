package com.luo.auth.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luo.common.base.po.BasePO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role")
public class RolePO extends BasePO {
    private Long roleGroupId;
    private String roleName;
}
