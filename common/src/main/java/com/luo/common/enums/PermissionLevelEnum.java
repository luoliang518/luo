package com.luo.common.enums;

import com.luo.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum PermissionLevelEnum implements BaseEnum<Integer>, Serializable {
    LEVEL1(1,"一级菜单"),
    LEVEL2(2,"二级菜单"),
    LEVEL3(3,"三级权限"),
    ;
    private final Integer code;
    private final String value;
}
