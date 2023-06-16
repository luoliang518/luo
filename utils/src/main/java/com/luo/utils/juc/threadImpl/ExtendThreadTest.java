package com.luo.utils.juc.threadImpl;

public class ExtendThreadTest extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 继承Thread类");
    }

    public static void main(String[] args) {
        //在循环中，您创建了100个ExtendThreadTest实例，并通过调用start()方法启动每个线程。然而，ExtendThreadTest类似于一个扩展了Thread类的自定义线程类，而不是实际的线程对象。
        //在Java中，每个线程对象只能启动一次。因此，在循环中多次启动相同的线程对象是不允许的，并且会引发IllegalThreadStateException异常。
//        ExtendThreadTest extendThreadTest = new ExtendThreadTest();
//        for (int i = 0; i < 100; i++) {
//            extendThreadTest.start();
//        }
        for (int i = 0; i < 100; i++) {
            ExtendThreadTest extendThreadTest = new ExtendThreadTest();
            extendThreadTest.start();
        }
    }
}
