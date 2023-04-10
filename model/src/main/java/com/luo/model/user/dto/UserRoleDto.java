package com.luo.model.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 14:12
 */
@Data
public class UserRoleDto implements Serializable {
    private static final long serialVersionUID=1;
    /**
     * 角色Id
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;

}
