package com.luo.util;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author luoliang
 * @description
 * @date 2024/1/18 16:57
 */
public class ExecutorService {
    public static ThreadPoolExecutor getExecutor(){
        return new ThreadPoolExecutor(
                4,
                4,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
