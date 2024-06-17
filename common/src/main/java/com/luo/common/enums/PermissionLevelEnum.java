package com.luo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionLevelEnum {
    LEVEL1(1,"一级菜单"),
    LEVEL2(2,"二级菜单"),
    LEVEL3(3,"三级权限"),
    ;
    private final int level;
    private final String desc;
}
