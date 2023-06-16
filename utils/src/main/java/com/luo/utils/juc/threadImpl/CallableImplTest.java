package com.luo.utils.juc.threadImpl;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableImplTest implements Callable {
    @Override
    public Object call() throws Exception{
        System.out.println(Thread.currentThread().getName() + " 实现Thread类");
        return Thread.currentThread().getName() + " 实现Thread类";
    }

    public static void main(String[] args) {
//        CallableImplTest callableImplTest = new CallableImplTest();
//            FutureTask futureTask = new FutureTask<>(callableImplTest);
//            for (int i = 0; i < 100; i++) {
//                Thread thread = new Thread(futureTask);
//                thread.start();
//        }
        //循环中多次使用相同的FutureTask对象来创建和启动线程。这是不正确的，会导致FutureTask对象在多个线程中重复执行，从而可能引发意外的结果。
        //如果您想创建和执行多个Callable任务，您需要为每个任务创建一个独立的FutureTask对象，并将其封装在Thread对象中。下面是修改后的示例代码：
            CallableImplTest callableImplTest = new CallableImplTest();
            for (int i = 0; i < 100; i++) {
                FutureTask futureTask = new FutureTask<>(callableImplTest);
                Thread thread = new Thread(futureTask);
                thread.start();
        }
    }
}
