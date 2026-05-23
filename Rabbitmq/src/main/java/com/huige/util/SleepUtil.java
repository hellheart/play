package com.huige.util;

public class SleepUtil {
    public static void sleep(int second){
        try {
            Thread.sleep(1000 * second);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
