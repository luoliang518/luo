package com.luo.model.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 14:12
 */
@Data
@TableName("user_role")
public class UserRoleDo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID=1;
    /**
     * 角色Id
     */
    @TableId("role_id")
    private String roleId;
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

}
