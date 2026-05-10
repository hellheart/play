package com.huige.Lock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    final static Object object = new Object();
    final static Object object1 = new Object();
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (object) {
                System.out.println(">>>>>>>>>我是t1线程。。。。");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (object1) {
                    System.out.println("正在获取t2资源。。。。。");
                }
            }

        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            synchronized (object1) {
                System.out.println(">>>>>>>>>我是t2线程。。。。");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (object) {
                    System.out.println("正在获取t1资源。。。。。");
                }
            }
        }, "t2").start();
    }
}
