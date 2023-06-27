package com.luo.model.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 16:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    /**
     * 用户账号 用于登录
     */
    private String account;
    private List<String> roles;
    /**
     * 用户密码
     */
    private String password;

}
