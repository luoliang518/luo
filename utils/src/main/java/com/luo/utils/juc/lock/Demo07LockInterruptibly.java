package com.luo.utils.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo07LockInterruptibly {

    private Lock lock = new ReentrantLock();

    // 小强：持续占用锁。
    public void useLock() {
        try {

            lock.lock();

            while (true) {
                System.out.println(Thread.currentThread().getName() + " 正在占用锁");
                try {
                    TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {}
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // 小明：痴痴地等待小强释放锁
    public void waitLock() {

        System.out.println(Thread.currentThread().getName() + " 线程启动了");

        try {

            // 通过 lockInterruptibly() 方法获取锁，在没有获取到锁的阻塞过程中可以被打断
            lock.lockInterruptibly();

            // ...

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

        System.out.println(Thread.currentThread().getName() + " 线程结束了");

    }

    public static void main(String[] args) {

        // 1、创建当前类对象
        Demo07LockInterruptibly demo = new Demo07LockInterruptibly();

        // 2、创建占用锁的线程（小强）
        new Thread(()->{

            demo.useLock();

        }, "thread-qiang").start();

        Thread thread = new Thread(() -> {

            demo.waitLock();

        }, "thread-ming");

        thread.start();

        try {
            TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {}

        // 打断小明线程的阻塞状态
        thread.interrupt();
    }
}