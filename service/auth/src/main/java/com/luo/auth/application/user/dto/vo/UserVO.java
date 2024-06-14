package com.luo.auth.application.user.dto.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Data
public class UserVO {
    private String userId;
    private String account;
    private List<UserTenantVo> userTenantVos;
}
