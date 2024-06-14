package com.luo.auth.infrastructure.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luo.auth.infrastructure.user.repository.po.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author luoliang
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO> {
}
