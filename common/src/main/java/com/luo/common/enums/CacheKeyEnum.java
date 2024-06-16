package com.luo.common.enums;

import com.luo.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheKeyEnum {
    UserLogin("USER_LOGIN", 1, "存入用户登录时状态"),
    SendEmailCode(CacheKeyEnum.UserLogin.create("SEND_EMAIL_CODE"),1,"用户注册/登录邮箱验证码 USER_LOGIN:SEND_CODE:email")
    ;
    private static final String CONNECTOR = ":";
    private final String prefix;
    private final int keySize;
    private final String desc;
    public String create(String... key) {
        if (key.length != keySize) {
            throw new ServiceException("该操作有误,请联系管理员进行处理");
        }
        StringBuilder sb = new StringBuilder(this.prefix);
        for (String s : key) {
            sb.append(CONNECTOR).append(s);
        }
        return sb.toString();
    }
}
