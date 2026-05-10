package com.huige.Lock;


import java.util.concurrent.TimeUnit;

class MyLock {
    public synchronized void m1() {
        System.out.println("发送邮件》》》》》》");
    }

    public static synchronized void m2() {
        System.out.println("发送短信>>>>>>>>>");
    }

    public void hello() {
        System.out.println("仅仅说额");

    }}


public class SynconizedDemo {
    public static void main(String[] args) {
        MyLock myLock = new MyLock();
        new Thread(() ->{
            myLock.m1();
        }, "thread1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> myLock.m2(), "thread2").start();
    }
}
