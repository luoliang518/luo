package com.luo.excelModel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 14:12
 */
@Data
@ExcelTarget("userRoleVo")
public class UserRoleVo implements Serializable {
    /**
     * 角色Id
     */
    @Excel(name="roleId")
    private String roleId;
    /**
     * 角色名称
     */
    @Excel(name="roleName")
    private String roleName;

}
