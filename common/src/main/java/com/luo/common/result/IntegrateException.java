package com.luo.common.result;

import com.luo.common.enums.ErrorBaseEnum;
import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

/**
 * 异常父类，支持params传参，可用于占位符处理<br/>
 * 提供静态工厂方法，要求必须传ErrorEnum的实现类
 *
 * @program: esign-tools
 * @description
 * @author: 罗亮
 * @create: 2022-11-14 15:06
 **/
@Data
public class IntegrateException extends RuntimeException implements ErrorBaseEnum {
    private static final long serialVersionUID = 1L;
    private Integer status;
    private String value;
    private String[] params;

    private IntegrateException(Integer status, Throwable cause) {
        this(status, null, cause);
    }

    private IntegrateException(Integer status, String message) {
        this(status, message, null);
    }

    private IntegrateException(Integer status, Throwable cause, String... params) {
        this(status, null, cause, params);
    }

    private IntegrateException(Integer status, String message, Throwable cause, String... params) {
        super(message, cause);
        if (params != null && params.length > 0) {
            setValue(String.format(message, (Object) params));
        }
        this.status = status;
        this.params = params;
    }

    public static <T extends ErrorBaseEnum> void buildExternalEx(T enumName) {
        throw new IntegrateException(enumName.getStatus(), enumName.getValue());
    }

    public static <T extends ErrorBaseEnum> void buildExternalEx(T enumName, String... params) {
        throw new IntegrateException(enumName.getStatus(), enumName.getValue(), null, params);
    }

    public static <T extends ErrorBaseEnum> void buildExternalEx(Throwable cause, T enumName, String... params) {
        throw new IntegrateException(enumName.getStatus(), enumName.getValue(), cause, params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntegrateException that = (IntegrateException) o;
        return status.equals(that.status) && value.equals(that.value) && Arrays.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(status, value);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }
}
