package com.athuige.demo.dynamicProxy.JDK;

import com.athuige.demo.MyCalss;
import com.athuige.demo.staticProxy.MyCalssImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInnovation implements InvocationHandler {
    private final Object myCalss;

    public MyInnovation(Object myCalss) {
        this.myCalss = myCalss;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy = 方法调用前");
        Object nihao = method.invoke(myCalss, args);
        System.out.println("nihao = 方法调用后");
        return nihao;
    }

    public static void main(String[] args) {
        MyCalssImpl myCalss1 = new MyCalssImpl();
        MyCalss instance = (MyCalss)Proxy.newProxyInstance(myCalss1.getClass().getClassLoader(), myCalss1.getClass().getInterfaces(), new MyInnovation(myCalss1));
        instance.sendMsg("hello");
    }
}
