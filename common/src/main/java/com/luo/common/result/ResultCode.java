package com.luo.common.result;

public enum ResultCode implements BaseCode {
    /**
     * 处理成功
     */
    OK(20000, "处理成功"),
    /**
     * 失败
     */
    FAILURE(40000, "业务异常"),
    ;

    private final Integer code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}