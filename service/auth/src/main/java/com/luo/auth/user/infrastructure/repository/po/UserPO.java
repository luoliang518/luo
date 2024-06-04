package com.luo.auth.user.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luo.common.po.BasePO;
/**
* @Description 用户实体类
* @author luoliang
*/
@TableName("user.sql")
public class UserPO extends BasePO {
    // 用户名
    private String username;
    // 账号
    private String account;
    // 密码
    private String password;
    // 头像
    private String avatar;
    // 邮箱
    private String email;
    // 手机号
    private String phone;
    // 所属租户id
    private String tenantId;
}
