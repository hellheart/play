package com.huige.Lock;

import java.util.concurrent.atomic.AtomicBoolean;

public class InteruptedDemo2 {
    private static final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开始");
        Thread t1 = new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "当前线程终端");
                    break;
                }
                System.out.println("1");
            }
        }, "t1");
        t1.start();
        Thread.sleep(10);
        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();

        System.out.println("主线程结束");
    }
}
