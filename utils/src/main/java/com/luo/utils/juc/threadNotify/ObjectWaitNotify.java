package com.luo.utils.juc.threadNotify;

public class ObjectWaitNotify {
    public static void main(String[] args) {
        ObjectWaitNotify objectWaitNotify = new ObjectWaitNotify();
        objectWaitNotify.waitTest();
        objectWaitNotify.notifyTest();
    }

    void waitTest() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+" waitTest is running...");
            try {
                synchronized (this){
                    wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+" waitTest is over...");

        }).start();
    }
    void notifyTest() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+" notifyTest is running...");
            synchronized (this){
                notify();
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+" notifyTest is over...");
        }).start();
    }
}
