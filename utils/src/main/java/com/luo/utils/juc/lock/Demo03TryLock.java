package com.luo.utils.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo03TryLock {

    private Lock lock = new ReentrantLock();

    public void showMessage() {

        boolean lockResult = false;

        try {

            // 尝试获取锁
            // 返回true：获取成功
            // 返回false：获取失败
            lockResult = lock.tryLock();

            if (lockResult) {
                try {
                    TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {}
                System.out.println(Thread.currentThread().getName() + " 得到了锁，正在工作");
            } else {
                System.out.println(Thread.currentThread().getName() + " 没有得到锁");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            // 如果曾经得到了锁，那么就解锁
            if (lockResult) {
                lock.unlock();
            }

        }

    }

    public static void main(String[] args) {

        // 1、创建多个线程共同操作的对象
        Demo03TryLock demo = new Demo03TryLock();

        // 2、创建三个线程
        new Thread(()->{

            for(int i = 0; i < 20; i++) {
                try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {}
                demo.showMessage();
            }

        }, "thread-01").start();

        new Thread(()->{

            for(int i = 0; i < 20; i++) {
                try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {}
                demo.showMessage();
            }

        }, "thread-02").start();

        new Thread(()->{

            for(int i = 0; i < 20; i++) {
                try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {}
                demo.showMessage();
            }

        }, "thread-03").start();
    }

}