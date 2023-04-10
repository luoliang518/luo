package com.luo.common.enums.unifiedEnums;

import com.luo.common.enums.ErrorBaseEnum;

public enum UnifiedServiceHandleEnumError implements ErrorBaseEnum {
        AUTHOR_NAME(0, "罗亮"),
        AUTHOR_PHONE(1, "17357170942"),
        STATUS_SUCCESS(200, "服务器成功返回"),
        STATUS_NOT_MODIFY(304, "自从上次请求后，请求的网页未修改过"),
        STATUS_ERROR(500, "服务器内部错误"),
        STATUS_NOT_FOUND(404, "Not Found"),
        STATUS_ACCESS_FORBIDDEN(403, "无权访问"),
        STATUS_UNAUTHORIZED(401, "未经授权的"),
        STATUS_REQUEST_TIMEOUT(408, "请求超时"),
        STATUS_METHOD_FAILURE(420, "请求方法失败"),
        STATUS_LOCKED(423, "被锁定"),
        STATUS_BAD_REQUEST(400, "错误请求"),
        SYS_REQUEST_FORMAT_ERROR(801, "请求格式错误，请重试"),
        SYS_REQUEST_EXCEED_LIMITS(802, "请求超过系统限制"),
        SYS_INTERFACE_PRIVILEGES_NOT_OPEN(803, "接口权限未开通"),
        SYS_SYSTEM_UNKNOWN_ERROR(804, "系统未知错误，请联系管理员"),
        SYS_ASSIGN_DATA_FAILURE(805, "获取指定数据失败"),
        SYS_LICENSE_EXPIRE(806, "系统许可异常/失效，请联系管理员"),
        CUSTOM_NO_LOGIN(901, "登录已过期"),
        CUSTOM_NAME_PASSWORD_ERROR(902, "用户名或密码不正确"),
        CUSTOM_PASSWORD_ERROR(903, "密码不正确"),
        CUSTOM_UN_SUPPORT_COOKIE(904, "不支持cookie信息！"),
        CUSTOM_VERIFICATION_CODE_EXPIRED(905, "验证码过期！"),
        CUSTOM_VERIFICATION_CODE_ERROR(906, "验证码错误！"),
        CUSTOM_EXCESSIVE_ATTEMPTS_ERROR(907, "用户名或密码错误次数大于5次,账户已锁定"),
        CUSTOM_DISABLED_ACCOUNT_ERROR(908, "帐号已经禁止登录"),
        CUSTOM_LOCKED_ACCOUNT_ERROR(909, "账户已锁定！"),
        CUSTOM_UNKNOWN_ACCOUNT_ERROR(910, "未知账户！"),
        CUSTOM_SESSION_INVALID_ERROR(911, "session失效！"),
        CUSTOM_QUERY_WITHOUT_RESULTS(912, "查询无结果"),
        CUSTOM_QUERY_PARAMETER_ERROR(913, "查询参数错误，请检查"),
        CUSTOM_INVALID_REQUEST(914, "无效的请求"),
        CUSTOM_PARAMETER_NULL(915, "输入的参数为NULL、“null”、空字符串"),
        CUSTOM_IDEMPOTENTCHECK_ERROR(916, "请勿重复提交！"),
        CUSTOM_SQL_INJECTION_ERROR(917, "包含非法字符，存在sql注入语句"),
        GET_IP_ADDRESS_FAIL(999, "当前IP地址获取失败"),
        ;

        private Integer status;
        private String value;

        private UnifiedServiceHandleEnumError(int status, String value) {
            this.status = status;
            this.value = value;
        }

        @Override
        public Integer getStatus() {
            return this.status;
        }
        @Override
        public String getValue() {
            return this.value;
        }
    }