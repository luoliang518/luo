package com.luo.common.context;
/**
* 当前登录用户信息存储线程容器
* @author luoliang
*/
public class UserContext {
    private static ThreadLocal<UserContextDto> threadLocal = new ThreadLocal<>();
    public static UserContextDto get() {
        return threadLocal.get();
    }

}
