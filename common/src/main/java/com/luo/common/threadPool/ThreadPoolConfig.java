package com.luo.common.threadPool;

import com.alibaba.ttl.threadpool.TtlExecutors;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration(proxyBeanMethods = false)
public class ThreadPoolConfig {
    @Value("${thread.core_pool_size:8}")
    private int corePoolSize;

    @Value("${thread.max_pool_size:20}")
    private int maxPoolSize;

    @Bean("ExecutorService")
    public ExecutorService getThreadPool() {
        return TtlExecutors.getTtlExecutorService(
                new ThreadPoolExecutor(
                        corePoolSize,
                        maxPoolSize,
                        60L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(),
                        new DefaultThreadFactory("luo-defaultThread-"),
                        new ThreadPoolExecutor.CallerRunsPolicy()));
    }
}
