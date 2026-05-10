package com.huige.Lock;

public class InteruptedDemo3 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {

            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(" = " + "被中断了");
                    break;
                }
                for (int i = 0; i < 300; i++) {
                    System.out.println("i = " + i);

                }

            }


        }, "t1");
        t1.start();
        System.out.println("Thread.currentThread().isInterrupted() = " + Thread.currentThread().isInterrupted());
        Thread.sleep(1000);
        // new Thread(() -> {
        //     t1.interrupt();
        // }, "t2").start();
        t1.interrupt();
    }
}
