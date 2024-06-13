package com.luo.auth.user.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luo.auth.user.infrastructure.repository.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author luoliang
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
