package com.luo.auth.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luo.auth.domain.roleAggregate.entity.Permission;
import com.luo.auth.domain.roleAggregate.entity.Role;
import com.luo.auth.domain.roleAggregate.entity.RoleGroup;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.domain.userAggregate.repository.UserRepository;
import com.luo.auth.infrastructure.converter.PermissionConverter;
import com.luo.auth.infrastructure.converter.RoleConverter;
import com.luo.auth.infrastructure.converter.RoleGroupConverter;
import com.luo.auth.infrastructure.converter.UserConverter;
import com.luo.auth.infrastructure.repository.mapper.*;
import com.luo.auth.infrastructure.repository.po.*;
import com.luo.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserPO> implements UserRepository {
    private final UserRoleGroupMapper userRoleGroupMapper;
    private final RoleGroupMapper roleGroupMapper;
    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionMapper permissionMapper;
    private final UserConverter userConverter;
    private final RoleGroupConverter roleGroupConverter;
    private final PermissionConverter permissionConverter;
    private final RoleConverter roleConverter;
    @Override
    @Transactional(readOnly = true)
    public User getUserByAccount(String account) {
        Assert.notNull(account, "用户账号不能为空");
        UserPO userPO = getOne(Wrappers.<UserPO>lambdaQuery().eq(UserPO::getAccount, account));
        if (userPO == null) {
            throw new ServiceException("用户不存在");
        }
        return userConverter.userPoToUser(userPO);
    }
    @Override
    public User getUserRoleGroup(User user) {
        List<UserRoleGroupPO> userRoleGroupPOs = userRoleGroupMapper.selectList(
                Wrappers.<UserRoleGroupPO>lambdaQuery().eq(UserRoleGroupPO::getUserId, user.getUserId()));
        if (userRoleGroupPOs.isEmpty()) {
            return user;
        }
        List<Long> roleGroupIds = userRoleGroupPOs.stream().map(UserRoleGroupPO::getRoleId).collect(Collectors.toList());
        List<RoleGroupPO> roleGroupPOs = roleGroupMapper.selectBatchIds(roleGroupIds);
        List<RoleGroup> roleGroups = roleGroupConverter.roleGroupPOsToRoleGroup(roleGroupPOs);
        user.setRoleGroups(roleGroups);
        return user;
    }

    @Override
    public User getUserRole(User user) {
        user.getRoleGroups().forEach(roleGroup -> {
            List<Long> roleIds = roleGroup.getRoles().stream().map(Role::getRoleId).collect(Collectors.toList());
            List<RolePO> rolePOs = roleMapper.selectBatchIds(roleIds);
            List<Role> roles = roleConverter.rolePOSToRoles(rolePOs);
            roleGroup.setRoles(roles);
        });
        return user;
    }

    @Override
    public User getUserPermission(User user) {
        user.getRoleGroups().stream().parallel().forEach(roleGroup -> {
            List<Long> roleIds = roleGroup.getRoles().stream().map(Role::getRoleId).collect(Collectors.toList());
            List<RolePermissionPO> rolePermissionPOs = rolePermissionMapper.selectList(
                    Wrappers.<RolePermissionPO>lambdaQuery().in(RolePermissionPO::getRoleId, roleIds));
            List<Long> permissionIds = rolePermissionPOs.stream().map(RolePermissionPO::getPermissionId).collect(Collectors.toList());
            List<PermissionPO> permissionPOs = permissionMapper.selectBatchIds(permissionIds);
            roleGroup.getRoles().forEach(role -> {
                List<Permission> permissions = permissionPOs.stream()
                        .filter(permissionPO -> rolePermissionPOs.stream()
                                .anyMatch(rp -> rp.getPermissionId().equals(permissionPO.getId()) && rp.getRoleId().equals(role.getRoleId())))
                        .map(permissionConverter::permissionPoToPermission)
                        .collect(Collectors.toList());
                role.setPermissions(permissions);
            });
        });
        return user;
    }
}
