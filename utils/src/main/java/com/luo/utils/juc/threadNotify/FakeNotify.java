package com.luo.utils.juc.threadNotify;

public class FakeNotify {
    public static void main(String[] args) {
        FakeNotify fakeNotify = new FakeNotify();
        for (int i = 0; i < 100; i++) {
            fakeNotify.compareEnglish();
            fakeNotify.compareChinese();
        }
    }

    int a = 0;

    void compareEnglish() {
        new Thread(() -> {
            synchronized (this) {
                try {
                    while (a<=0) {
                        wait();
                        a++;
                    }
                    System.out.println(Thread.currentThread().getName()+" a=" + a);
                    notifyAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    void compareChinese() {
        new Thread(() -> {
            synchronized (this) {
                try {
                    while (a>0) {
                        wait();
                        a--;
                    }
                    System.out.println(Thread.currentThread().getName()+" a=" + a);
                    notifyAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
