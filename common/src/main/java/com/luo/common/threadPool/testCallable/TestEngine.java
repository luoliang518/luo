package com.luo.common.threadPool.testCallable;

import io.netty.util.concurrent.DefaultEventExecutor;
import org.springframework.beans.factory.DisposableBean;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TestEngine implements DisposableBean {
    @Resource
    private final ExecutorService executorService=new DefaultEventExecutor();

    public Future submit(TestCallable testCallable){
        return executorService.submit(testCallable);
    }
    @Override
    public void destroy() {
        executorService.shutdown();
    }
}
