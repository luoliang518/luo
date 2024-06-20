package com.luo.auth.domain.roleAggregate.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luo.auth.domain.roleAggregate.entity.RoleGroup;
import com.luo.auth.domain.roleAggregate.repository.RoleGroupRepository;
import com.luo.auth.infrastructure.converter.RoleGroupConverter;
import com.luo.auth.infrastructure.repository.po.RoleGroupPO;
import com.luo.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl {
    private final RoleGroupRepository roleGroupRepository;
    private final RoleGroupConverter roleGroupConverter;
    public void addOrEditRoleGroup(RoleGroup roleGroup) {
        RoleGroupPO roleGroupPO = null;
            roleGroupPO = roleGroupConverter.roleGroupToRoleGroupPO(roleGroup);


        // 校验是否已存在同名角色组
        if (roleGroupPO.getId()!=null) {
            roleGroupRepository.getOneOpt(
                            Wrappers.lambdaQuery(roleGroupPO)
                                    .eq(RoleGroupPO::getRoleGroupName, roleGroupPO.getRoleGroupName()))
                    .ifPresent(one->{
                        throw new ServiceException("已存在同名角色组");
                    });
        }
        roleGroupRepository.saveOrUpdate(roleGroupPO);
    }

    public List<RoleGroup> getRoleGroupList() {
        List<RoleGroupPO> roleGroupPOS = roleGroupRepository.list();
        return roleGroupConverter.roleGroupPOsToRoleGroup(roleGroupPOS);
    }
}
