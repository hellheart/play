package com.athuige.demo.staticProxy;

import com.athuige.demo.MyCalss;

public class MyCalssImpl implements MyCalss {

    public final String heoo() {
        System.out.println("some");
        return "some";
    }
    @Override
    public String sendMsg(String message) {
        System.out.println(message);
        return message;
    }
}
