package com.luo.auth.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luo.auth.infrastructure.repository.po.PermissionPO;
import com.luo.auth.infrastructure.repository.po.TenantUserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author luoliang
 */
@Mapper
public interface TenantUserMapper extends BaseMapper<TenantUserPO> {
}
