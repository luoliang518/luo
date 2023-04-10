package com.luo.common.result;

import com.luo.common.enums.unifiedEnums.UnifiedServiceHandleEnumError;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: luoliang
 * @DATE: 2023/1/29/029
 * @TIME: 15:13
 */
@Data
public class UnifiedServiceHandle<T> implements Serializable {
    private static final long serialVersionUID =1;
    private boolean success;
    private int status;
    private String message;
    private T data;

    private UnifiedServiceHandle(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private UnifiedServiceHandle() {
    }

    public static <T> UnifiedServiceHandle<T> getSuccessResult(int status, String message, T data) {
        UnifiedServiceHandle<T> model = new UnifiedServiceHandle();
        model.setSuccess(true);
        model.setStatus(status);
        model.setMessage(message);
        model.setData(data);
        return model;
    }

    public static <T> UnifiedServiceHandle<T> getSuccessResult(int status, String message) {
        UnifiedServiceHandle<T> model = new UnifiedServiceHandle();
        model.setSuccess(true);
        model.setStatus(status);
        model.setMessage(message);
        return model;
    }

    public static <T> UnifiedServiceHandle<T> SUCCESS() {
        return getSuccessResult(UnifiedServiceHandleEnumError.STATUS_SUCCESS.getStatus(), UnifiedServiceHandleEnumError.STATUS_SUCCESS.getValue());
    }

    public static <T> UnifiedServiceHandle<T> SUCCESS(T data) {
        return getSuccessResult(UnifiedServiceHandleEnumError.STATUS_SUCCESS.getStatus(), UnifiedServiceHandleEnumError.STATUS_SUCCESS.getValue(), data);
    }

    public static <T> UnifiedServiceHandle<T> SUCCESS(String message) {
        return getSuccessResult(UnifiedServiceHandleEnumError.STATUS_SUCCESS.getStatus(), message);
    }

    public static <T> UnifiedServiceHandle<T> SUCCESS(String message, T data) {
        return getSuccessResult(UnifiedServiceHandleEnumError.STATUS_SUCCESS.getStatus(), message, data);
    }

    public static <T> UnifiedServiceHandle<T> getFailureResult(int status, String message, T data) {
        UnifiedServiceHandle<T> model = new UnifiedServiceHandle();
        model.setSuccess(false);
        model.setStatus(status);
        model.setMessage(message);
        model.setData(data);
        return model;
    }

    public static <T> UnifiedServiceHandle<T> getFailureResult(int status, String message) {
        UnifiedServiceHandle<T> model = new UnifiedServiceHandle();
        model.setSuccess(false);
        model.setStatus(status);
        model.setMessage(message);
        return model;
    }
}
