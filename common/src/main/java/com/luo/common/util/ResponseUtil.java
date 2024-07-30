package com.luo.common.util;

import com.luo.common.exception.ServiceException;
import com.luo.common.result.BaseCode;
import com.luo.common.result.Response;
import com.luo.common.result.ResultCode;

import java.util.Map;

public class ResponseUtil {

    /**
     * 生成没有数据和额外数据的成功响应
     *
     * @return {@link Response}
     */
    public static Response<?> success() {
        return new Response<>(true, ResultCode.OK);
    }

    /**
     * 生成带数据的成功响应
     *
     * @param data 响应的数据
     * @return {@link Response}
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(true, ResultCode.OK, data);
    }
    public static <T> Response<T> success(BaseCode baseCode) {
        return new Response<>(true, baseCode);
    }
    public static <T> Response<T> success(BaseCode baseCode,T data) {
        return new Response<>(true, baseCode, data);
    }
    /**
     * 生成带数据和额外数据的成功响应
     *
     * @param data  响应的数据
     * @param extra 响应的额外数据
     * @return {@link Response}
     */
    public static <T> Response<T> success(T data, Map<String, Object> extra) {
        return new Response<>(true, ResultCode.OK, data, extra);
    }

    /**
     * 根据success布尔值构造返回值
     *
     * @param success {@link Boolean}
     * @return {@link Response}
     */
    public static Response<?> result(boolean success) {
        return success ? ResponseUtil.success() : error(ResultCode.FAILURE);
    }

    /**
     * 生成自定状态码的响应
     *
     * @param code 通用状态码
     * @return {@link Response}
     */
    public static Response<?> error(BaseCode code) {
        Response<?> result = new Response<>(false, code);
        if (code.equals(ResultCode.OK)) {
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 生成自定状态码的响应
     *
     * @param code 通用状态码
     * @return {@link Response}
     */
    public static Response<?> error(BaseCode code, String msg) {
        Response<?> result = new Response<>(false, code, msg);
        if (code.equals(ResultCode.OK)) {
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 生成自定状态码且带数据的响应
     *
     * @param code 通用状态码
     * @param data 响应的数据
     * @return {@link Response}
     */
    public static <T> Response<T> error(BaseCode code, T data) {
        Response<T> result = new Response<>(false, code, data);
        if (code.equals(ResultCode.OK)) {
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 生成自定状态码且带数据和额外数据的响应
     *
     * @param code  通用状态码
     * @param data  响应的数据
     * @param extra 响应的额外数据
     * @return {@link Response}
     */
    public static <T> Response<T> error(BaseCode code, T data, Map<String, Object> extra) {
        Response<T> result = new Response<>(false, code, data, extra);
        if (code.equals(ResultCode.OK)) {
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 使用自定义异常生成响应
     *
     * @param exception BaseException或其子类对象
     * @return {@link Response}
     */
    public static Response<?> error(ServiceException exception) {
        return new Response<>(false, exception.getCode(), exception.getMsg());
    }

    /**
     * 使用自定义异常生成带数据的响应
     *
     * @param exception BaseException或其子类对象
     * @param data      响应的数据
     * @return {@link Response}
     */
    public static <T> Response<T> error(ServiceException exception, T data) {
        return new Response<>(false, exception.getCode().getCode(), exception.getMsg(), data);
    }

    /**
     * 使用自定义异常生成带数据和额外数据的响应
     *
     * @param exception BaseException或其子类对象
     * @param data      响应的数据
     * @param extra     响应的额外数据
     * @return {@link Response}
     */
    public static <T> Response<T> error(ServiceException exception, T data, Map<String, Object> extra) {
        return new Response<>(false, exception.getCode().getCode(), exception.getMsg(), data, extra);
    }

    /**
     * 为响应添加额外数据
     *
     * @param result 需要添加额外数据的BaseResult对象
     * @param key    额外数据的key
     * @param value  额外数据的值
     */
    public static void putExtraNoData(Response<?> result, String key, Object value) {
        result.addExtra(key, value);
    }

    /**
     * 若条件为真，则为响应添加额外数据
     *
     * @param result    需要添加额外数据的BaseResult对象
     * @param condition 判断条件
     * @param key       额外数据的键
     * @param value     额外数据的值
     */
    public static void putExtraIfTrueNoData(Response<?> result, boolean condition, String key, Object value) {
        result.addExtraIfTrue(condition, key, value);
    }

    /**
     * 为响应添加额外数据
     *
     * @param result 需要添加额外数据的BaseResult对象
     * @param key    额外数据的key
     * @param value  额外数据的值
     */
    public static <T> void putExtra(Response<T> result, String key, Object value) {
        result.addExtra(key, value);
    }

    /**
     * 若条件为真，则为响应添加额外数据
     *
     * @param result    需要添加额外数据的BaseResult对象
     * @param condition 判断条件
     * @param key       额外数据的键
     * @param value     额外数据的值
     */
    public static <T> void putExtraIfTrue(Response<T> result, boolean condition, String key, Object value) {
        result.addExtraIfTrue(condition, key, value);
    }

}