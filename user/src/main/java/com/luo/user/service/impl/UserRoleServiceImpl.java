package com.luo.user.service.impl;

import com.luo.common.enums.unifiedEnums.OperateUserEnum;
import com.luo.common.result.IntegrateException;
import com.luo.user.mapper.UserRoleMapper;
import com.luo.user.service.UserRoleService;
import com.luo.model.jopoMapper.UserFiledMapper;
import com.luo.model.user.entity.UserRoleDo;
import com.luo.model.user.dto.UserRoleDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: luoliang
 * @DATE: 2023/1/30/030
 * @TIME: 10:48
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Override
    public void createRole(UserRoleDto userRoleDto) {
        UserRoleDo userRoleDo = UserFiledMapper.INSTANCE.userRoleDto2Do(userRoleDto);
        int insert = userRoleMapper.insert(userRoleDo);
        if (insert<1){
            IntegrateException.buildExternalEx(OperateUserEnum.CREATE_ROLE_FAIL);
        }
    }
}
