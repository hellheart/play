package com.athuige.demo.staticProxy;

import com.athuige.demo.MyCalss;

public class MainHH {
    public static void main(String[] args) {
        MyCalssImpl myCalss = new MyCalssImpl();
        MyClassStaticImpl myClassStatic = new MyClassStaticImpl(myCalss);
        myClassStatic.sendMsg("nimei");
        myClassStatic.heoo();
    }
}
