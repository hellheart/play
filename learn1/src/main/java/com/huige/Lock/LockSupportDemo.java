package com.huige.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {
        //m1();
        //reentantlock();
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "come in");
            LockSupport.park();
                System.out.println(Thread.currentThread().getName() + "被唤醒");
        }, "t1");
        t1.start();

        new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "通知");
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
        }, "t2").start();

    }

    private static void reentantlock() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "被唤醒");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "通知");
                condition.signal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void m1() throws InterruptedException {
        Object o = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (o) {
                System.out.println(Thread.currentThread().getName() + "come in");
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "被唤醒");
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        new Thread(() -> {
            synchronized (o) {
                System.out.println(Thread.currentThread().getName() + "通知");
                o.notify();
            }
        }, "t2").start();
    }
}
