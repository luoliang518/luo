package com.luo.model.user;

import lombok.Data;

import java.util.List;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 16:35
 */
@Data
public class UserDto {
    /**
     * 用户账号 用于登录
     */
    private String account;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户手机号 唯一
     */
    private String phoneNumber;
    /**
     * 用户角色id
     */
    private List<String> rolesIds;

}
