package com.luo.common.threadPool.testCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class TestFuture {
    public static void main(String[] args) throws Exception {
        List<Future<String>> futures = new ArrayList<>();
        TestCallable testCallable = new TestCallable();
        TestEngine testEngine = new TestEngine();
        futures.add(testEngine.submit(testCallable));
    }
}
