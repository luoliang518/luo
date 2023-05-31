package com.luo.spring.aop;

public class LuoAopContext {
    public static final ThreadLocal<Object> currentProxy = new ThreadLocal<>();

    private LuoAopContext() {
    }

    /**
     * 获取当前正在运行的 AOP 代理对象
     * @return
     */
    public static Object currentProxy() {
        Object proxy = currentProxy.get();
        if (proxy == null) {
            throw new IllegalStateException("当前没有代理在运行");
        }
        return proxy;
    }

    /**
     * 将当前代理对象设置到 ThreadLocal 中 返回之前的代理对象
     * @param proxy
     * @return
     */
    public static Object setCurrentProxy(Object proxy) {
        Object old = currentProxy.get();
        if (proxy != null) {
            currentProxy.set(proxy);
        } else {
            currentProxy.remove();
        }
        return old;
    }
}
