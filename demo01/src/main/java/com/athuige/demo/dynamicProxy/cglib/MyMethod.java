package com.athuige.demo.dynamicProxy.cglib;

import com.athuige.demo.staticProxy.MyCalssImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethod implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用方法前。。。。。。。。。");
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("调用方法后。。。。。。。。。");
        return o1;
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new MyMethod());
        enhancer.setClassLoader(MyCalssImpl.class.getClassLoader());
        enhancer.setSuperclass(MyCalssImpl.class);
        MyCalssImpl o = (MyCalssImpl)enhancer.create();
        o.sendMsg("hello");
        o.heoo();
    }
}
