package com.luo.model.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 10:36
 */
@Data
@TableName("user")
public class UserDo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID=1;
    /**
     * 用户ID
     */
    @TableId(value = "user_id",type = IdType.AUTO)
    private String userId;
    /**
     * 用户账号 用于登录
     */
    @TableField("account")
    private String account;
    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    /**
     * 用户密码
     */
    @TableField("password")
    private String password;
    /**
     * 用户手机号 唯一
     */
    @TableField("phone_number")
    private String phoneNumber;
    /**
     * 用户角色id JSON数组
     */
    @TableField("user_roles_ids")
    private String userRolesIds;
    /**
     * 用户角色
     */
    @TableField(exist = false)
    private List<UserRoleDo> userRoleDos;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;
    /**
     * 修改时间
     */
    @TableField("modify_time")
    private String modifyTime;
    /**
     * 是否删除 （0未删除 1已删除）
     */
    @TableField("deleted")
    private String deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDo userDo = (UserDo) o;
        return userId.equals(userDo.userId) && account.equals(userDo.account) && username.equals(userDo.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, account, username);
    }
}
