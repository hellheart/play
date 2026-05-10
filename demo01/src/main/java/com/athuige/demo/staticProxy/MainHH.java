package com.athuige.demo.staticProxy;

public class Main {
    public static void main(String[] args) {
        MyCalssImpl myCalss = new MyCalssImpl();
        MyClassStaticImpl myClassStatic = new MyClassStaticImpl(myCalss);
        myClassStatic.sendMsg("nimei");
    }
}
