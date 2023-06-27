package com.luo.login.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.luo.common.enums.unifiedEnums.OperateUserEnum;
import com.luo.common.result.IntegrateException;
import com.luo.login.mapper.UserMapper;
import com.luo.login.mapper.UserRoleMapper;
import com.luo.login.service.UserService;
import com.luo.model.jopoMapper.UserFiledMapper;
import com.luo.model.user.entity.UserDo;
import com.luo.model.user.dto.UserDto;
import com.luo.model.user.entity.UserRoleDo;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 10:26
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDo getUserByAccount(String userAccount) {
        UserDo userDo = userMapper.selectOne(new LambdaQueryWrapper<UserDo>().eq(UserDo::getAccount, userAccount));
        String userRolesIds = userDo.getUserRolesIds();
        JSONArray jsonArray = JSONArray.parseArray(userRolesIds);
        List<UserRoleDo> userRoleDos = userRoleMapper.selectList(new LambdaQueryWrapper<UserRoleDo>().in(UserRoleDo::getRoleId, jsonArray));
        userDo.setUserRoleDos(userRoleDos);
        return userDo;
    }

    @Override
    public void createUser(UserDto userDto) {
        UserDo userDo = UserFiledMapper.INSTANCE.userDto2Do(userDto);
        List<String> rolesIds = userDto.getRolesIds();
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(rolesIds));
        userDo.setUserRolesIds(array.toString());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userDo.setPassword(encoder.encode(userDo.getPassword()));
        int insert = userMapper.insert(userDo);
        if (insert<1){
            IntegrateException.buildExternalEx(OperateUserEnum.CREATE_USER_FAIL);
        }
    }

    @Override
    public void checkUser(String userAccount, String password) {
        UserDo userDo = userMapper.selectOne(new LambdaQueryWrapper<UserDo>().eq(UserDo::getAccount, userAccount));
        if (userDo==null){
            IntegrateException.buildExternalEx(OperateUserEnum.USER_NOT_EXIST);
        }
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        boolean matches = encoder.matches(password, userDo.getPassword());
        if (!matches){
            IntegrateException.buildExternalEx(OperateUserEnum.USER_PASSWORD_ERROR);
        }
    }
}
