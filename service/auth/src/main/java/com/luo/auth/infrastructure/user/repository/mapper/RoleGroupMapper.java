package com.luo.auth.infrastructure.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luo.auth.infrastructure.user.repository.po.RoleGroupPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author luoliang
 */
@Mapper
public interface RoleGroupMapper extends BaseMapper<RoleGroupPO> {
}
