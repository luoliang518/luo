package com.luo.common.exception;

import com.luo.common.result.BaseCode;
import com.luo.common.result.ResultCode;
import lombok.Getter;

import java.io.Serial;

/**
 * @Description 业务异常返回
 * @Author luoliang
 */
@Getter
public class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -412302766008471476L;

    private final BaseCode code;

    private final String msg;

    /**
     * 构造函数
     * 传入异常消息
     * 返回码默认为ResultCode.FAILURE
     *
     * @param message 异常消息
     */
    public ServiceException(String message) {
        super(message);
        this.msg = message;
        this.code = ResultCode.FAILURE;
    }

    public ServiceException(Throwable cause) {
        super(cause.getMessage(), cause);
        this.msg = cause.getMessage();
        this.code = ResultCode.FAILURE;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
        this.code = ResultCode.FAILURE;
    }

    /**
     * 构造函数
     * 使用通用状态码新建异常
     *
     * @param code 异常码
     */
    public ServiceException(BaseCode code) {
        super(code.getMessage());
        this.code = code;
        this.msg = code.getMessage();
    }

    /**
     * 构造函数
     * 自定义异常消息和返回码
     *
     * @param message 异常消息
     * @param code 异常码
     */
    public ServiceException(BaseCode code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

}
