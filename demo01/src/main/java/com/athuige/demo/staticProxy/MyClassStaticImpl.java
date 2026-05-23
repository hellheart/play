package com.athuige.demo.staticProxy;

import com.athuige.demo.MyCalss;

public class MyClassStaticImpl implements MyCalss {
    private final MyCalssImpl myCalss;

    public MyClassStaticImpl(MyCalssImpl myCalss) {
        this.myCalss = myCalss;
    }

    @Override
    public String sendMsg(String message) {
        System.out.println("静态代理类的我先来");
        return myCalss.sendMsg(message);
    }

    public final String heoo() {
        myCalss.heoo();
        System.out.println("some");
        return "some";
    }
}
