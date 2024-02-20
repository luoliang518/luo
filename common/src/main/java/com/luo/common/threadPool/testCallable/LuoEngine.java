package com.luo.common.threadPool.testCallable;

import io.netty.util.concurrent.DefaultEventExecutor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class LuoEngine implements DisposableBean {
    @Resource
    private final ExecutorService executorService=new DefaultEventExecutor();

    public Future submit(LuoCallable luoCallable){
        return executorService.submit(luoCallable);
    }
    @Override
    public void destroy() {
        executorService.shutdown();
    }
}
