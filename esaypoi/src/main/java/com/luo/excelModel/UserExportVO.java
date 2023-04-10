package com.luo.excelModel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ExcelTarget("user")
public class UserExportVO implements Serializable {

    /**
     * 用户ID
     */
    @Excel(name="userId",width = 20,addressList = true,dict="id")
    private String userId;
    /**
     * 用户账号 用于登录
     */
    @Excel(name="account")
    private String account;
    /**
     * 用户名
     */
    @Excel(name="username")
    private String username;
    /**
     * 用户密码
     */
    @Excel(name="password")
    private String password;
    /**
     * 用户手机号 唯一
     */
    @Excel(name="phoneNumber")
    private String phoneNumber;
    /**
     * 用户角色id JSON数组
     */
    @ExcelIgnore
    private String userRolesIds;
    /**
     * 用户角色
     */
    @ExcelCollection(name = "userRoleVo")
    private List<UserRoleVo> userRoleDos;
    /**
     * 创建时间
     */
    @Excel(name="createTime")
    private String createTime;
    /**
     * 修改时间
     */
    @Excel(name="modifyTime")
    private String modifyTime;
    /**
     * 是否删除 （0未删除 1已删除）
     */
    @Excel(name="是否删除",replace = {"未删除_0", "已删除_1"},addressList=true)
    private String deleted;
}