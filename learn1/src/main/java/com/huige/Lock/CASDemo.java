package com.huige.Lock;

public class CASDemo {
    volatile  int num;

    public int getNum() {
        return num;
    }

    public synchronized void  setNum() {
        num++;
    }

    public static void main(String[] args) {
        CASDemo casDemo = new CASDemo();
        for (int i = 1; i <= 1000; i++) {
            new Thread(() -> {
                casDemo.setNum();
            }).start();
        }
        System.out.println("casDemo = " + casDemo.num);
    }

}
