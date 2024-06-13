package com.luo.auth.user.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luo.auth.user.infrastructure.repository.po.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author luoliang
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO> {
}
