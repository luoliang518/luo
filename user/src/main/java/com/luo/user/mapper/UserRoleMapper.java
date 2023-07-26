package com.luo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luo.model.user.entity.UserRoleDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: luoliang
 * @DATE: 2023/1/30/030
 * @TIME: 10:35
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDo> {
}
