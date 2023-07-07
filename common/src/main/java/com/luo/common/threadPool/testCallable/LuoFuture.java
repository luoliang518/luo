package com.luo.common.threadPool.testCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class LuoFuture {
    public static void main(String[] args) throws Exception {
        List<Future<String>> futures = new ArrayList<>();
        LuoCallable luoCallable = new LuoCallable();
        LuoEngine luoEngine = new LuoEngine();
        futures.add(luoEngine.submit(luoCallable));
    }
}
