package com.luo.utils.juc.threadImpl;

import java.util.Timer;
import java.util.TimerTask;

public class TimeSchedule {
    public static void main(String[] args) {
        // 每个定时任务都会创建一个新的线程
        for (int i = 0; i < 100; i++) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " 定时任务");
                }
            }, 0, 100);
        }
    }
}
