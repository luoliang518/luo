package com.luo.common.enums;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 15:20
 * 所有抛出异常都需要实现此类
 */
public interface BaseEnum {
    /**
     * 获取枚举状态
     * @return
     */
    Integer getStatus();

    /**
     * 获取枚举值
     * @return
     */
    String  getValue();
}
