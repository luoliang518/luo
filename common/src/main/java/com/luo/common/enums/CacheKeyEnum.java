package com.luo.common.enums;

import com.luo.common.base.BaseEnum;
import com.luo.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheKeyEnum implements BaseEnum<String> {
    Lock("LOCK",1,"各类锁状态 前缀"),
    GenerateToken(CacheKeyEnum.Lock.create("GENERATE_TOKEN"),1,"用户登录时生成token锁"),
    UserLogin("USER_LOGIN", 1, "存入用户登录信息 前缀"),
    UserInfo("USER_INFO", 0, "存入用户登录时信息"),
    UserToken("USER_TOKEN", 0, "存入用户登录时令牌"),
    SendEmailCode(CacheKeyEnum.UserLogin.create("SEND_EMAIL_CODE"),1,"用户注册/登录邮箱验证码 USER_LOGIN:SEND_CODE:email"),
    User(CacheKeyEnum.UserLogin.create("USER_TOKEN"),3,"用户登录信息 USER_LOGIN:USER_TOKEN:account:ip:token/user")
    ;
    private static final String CONNECTOR = ":";
    private final String code;
    private final int keySize;
    private final String value;
    public String create(String... key) {
        if (key.length != keySize) {
            throw new ServiceException("该操作有误,请联系管理员进行处理");
        }
        StringBuilder sb = new StringBuilder(this.code);
        for (String s : key) {
            sb.append(CONNECTOR).append(s);
        }
        return sb.toString();
    }
}
