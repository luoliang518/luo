package com.luo.auth.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luo.common.base.po.BasePO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role")
public class PermissionPO extends BasePO {
}
