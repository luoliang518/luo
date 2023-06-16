package com.luo.utils.juc.threadImpl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolImplTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Future<?> submit = executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "callable or runnable is running...");
                return Thread.currentThread().getName() + "callable is running...";
            });
            Object o = submit.get();
            System.out.println(o);
        }
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "execute is running...");
            });
        }
        executorService.shutdown();
    }
}
