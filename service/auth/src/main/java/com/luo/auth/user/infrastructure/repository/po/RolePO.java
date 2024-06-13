package com.luo.auth.user.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luo.common.base.po.BasePO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role")
public class RolePO extends BasePO {
}
