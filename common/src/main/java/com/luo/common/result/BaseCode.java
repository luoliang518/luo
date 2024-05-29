package com.luo.common.result;
/**
 * @Description 所有返回状态应当实现该接口
 * @Author luoliang
 */

public interface BaseCode {

    /**
     * 状态码的code
     *
     * @return Integer
     */
    Integer getCode();

    /**
     * 状态码的message
     *
     * @return String
     */
    String getMessage();

}