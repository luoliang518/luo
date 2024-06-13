package com.luo.auth.user.domain.user.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/13
 */
@Data
public class RoleGroup {
    private List<Role> roles;
}
