package com.luo.common.context.user;
/**
* 当前登录用户信息存储线程容器
* @author luoliang
*/
public class UserContextHolder {
    private static ThreadLocal<UserContext> threadLocal = new ThreadLocal<>();
    public static UserContext get() {
        return threadLocal.get();
    }
    public static void set(UserContext userContext) {
        threadLocal.set(userContext);
    }

}
