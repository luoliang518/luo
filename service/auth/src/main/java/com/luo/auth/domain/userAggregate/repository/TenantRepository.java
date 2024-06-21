package com.luo.auth.domain.userAggregate.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import com.luo.auth.infrastructure.repository.po.TenantUserPO;
import com.luo.auth.infrastructure.repository.po.UserPO;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/21
 */
public interface TenantRepository extends IService<TenantPO> {
    List<TenantPO> getTenantListByUserId(Long userId);
}
