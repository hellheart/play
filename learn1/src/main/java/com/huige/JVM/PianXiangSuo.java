package com.huige.JVM;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class PianXiangSuo {

    public static void main(String[] args) {
        // 偏向锁默认开启延时4s
        // try {
        //     TimeUnit.SECONDS.sleep(5);
        // } catch (InterruptedException e) {
        //     throw new RuntimeException(e);
        // }
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            o.hashCode();
            System.out.println("调用了偏向锁的hashcode");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());

            // AbstractQueuedSynchronizer
        }

    }
}
