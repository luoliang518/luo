package com.luo.common.enums;

import com.luo.common.base.BaseEnum;
import com.luo.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheKeyEnum implements BaseEnum<String> {
    Lock("LOCK","各类锁状态 前缀"),
    GenerateToken(CacheKeyEnum.Lock.create("GENERATE_TOKEN"),"用户登录时生成token锁"),
    /* 后缀 */
    User("USER",  "存入用户登录信息 前缀"),
    UserInfo("USER_INFO", "存入用户登录时信息"),
    UserToken("USER_TOKEN", "存入用户登录时令牌"),
    UserNewToken("USER_NEW_TOKEN",  "存入用户令牌续命令牌"),
    UserLogin(CacheKeyEnum.User.create("USER_LOGIN"),"用户登录信息 USER_LOGIN:USER_TOKEN:account:ip:token/user"),

    SendEmailCode(CacheKeyEnum.User.create("SEND_EMAIL_CODE"),"用户注册/登录邮箱验证码 USER_LOGIN:SEND_CODE:email"),
    ;
    private static final String CONNECTOR = ":";
    private final String code;
    private final String value;
    public String create(String... key) {
        StringBuilder sb = new StringBuilder(this.code);
        for (String s : key) {
            sb.append(CONNECTOR).append(s);
        }
        return sb.toString();
    }
}
