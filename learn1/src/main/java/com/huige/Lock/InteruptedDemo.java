package com.huige.Lock;

public class InteruptedDemo {
    private volatile static boolean flag;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (flag) {
                System.out.println(Thread.currentThread().getName() + "当前线程终端");
                break;
            }
        }, "t1");
        t1.start();
        Thread.sleep(10);
        new Thread(() -> {
            flag = true;
        }, "t2").start();
    }
}
