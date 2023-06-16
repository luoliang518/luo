package com.luo.utils.juc.threadImpl;

public class RunnableImplTest implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 实现Runnable接口");
    }

    public static void main(String[] args) {
        RunnableImplTest runnableImplTest = new RunnableImplTest();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnableImplTest);
            thread.start();
        }
    }
}
