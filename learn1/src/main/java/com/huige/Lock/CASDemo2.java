package com.huige.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo2 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        atomicInteger.compareAndSet(1,2);
        System.out.println(Thread.currentThread().getName() + "\t" + atomicInteger.get());
        System.out.println("atomicInteger.compareAndSet(1,2022) = " + atomicInteger.compareAndSet(1, 2022));
    }
}
