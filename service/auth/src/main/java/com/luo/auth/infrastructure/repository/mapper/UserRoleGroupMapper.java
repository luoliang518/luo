package com.luo.auth.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luo.auth.infrastructure.repository.po.UserRoleGroupPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author luoliang
 */
@Mapper
public interface UserRoleGroupMapper extends BaseMapper<UserRoleGroupPO> {
}
