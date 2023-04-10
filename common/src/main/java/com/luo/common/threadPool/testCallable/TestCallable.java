package com.luo.common.threadPool.testCallable;

import java.util.concurrent.Callable;

public class TestCallable<T> implements Callable<T> {
    @Override
    public T call() throws Exception {
        System.out.println("Hello,I'm TestCallable");
        return null;
    }
}
