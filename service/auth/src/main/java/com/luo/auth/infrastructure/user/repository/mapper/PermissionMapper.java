package com.luo.auth.infrastructure.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luo.auth.infrastructure.user.repository.po.PermissionPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author luoliang
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionPO> {
}
