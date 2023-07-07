package com.luo.common.threadPool.testCallable;

import java.util.concurrent.Callable;

public class LuoCallable<T> implements Callable<T> {
    @Override
    public T call() throws Exception {
        System.out.println("Hello,I'm LuoCallable");
        return null;
    }
}
