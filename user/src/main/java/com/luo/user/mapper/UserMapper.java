package com.luo.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luo.model.user.entity.UserDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 10:27
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDo> {
}
