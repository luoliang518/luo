package com.luo.common.enums.unifiedEnums;

import com.luo.common.enums.BaseEnum;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 15:40
 * 1000 - 1200
 */
public enum OperateUserEnum implements BaseEnum {
    CREATE_USER_SUCCESS(1000,"创建用户成功"),
    CREATE_USER_FAIL(1001,"创建用户失败"),
    CREATE_ROLE_SUCCESS(1002,"创建角色成功"),
    CREATE_ROLE_FAIL(1003,"创建角色失败"),
    USER_NOT_EXIST(1004, "用户不存在"),
    USER_PASSWORD_ERROR(1005, "用户密码错误"),
    GET_TOKEN_SUCCESS(1006, "获取token成功"),
    CHECK_TOKEN_SUCCESS(1007, "校验token成功"),

    ;

    private Integer status;
    private String value;

    OperateUserEnum(int status, String value) {
        this.status = status;
        this.value = value;
    }
    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public String getValue() {
        return value;
    }
}
