package com.luo.login.service;

import com.luo.model.user.dto.UserRoleDto;

/**
 * @author: luoliang
 * @DATE: 2023/1/30/030
 * @TIME: 10:48
 */
public interface UserRoleService {
    /**
     * 创建角色
     * @param userRoleDto
     */
    void createRole(UserRoleDto userRoleDto);
}
