package org.example.dao;

public interface DefaultIntefaceTest {

    default void f() {
        System.out.println("default::f()");
    }

    //void come();
   DefaultIntefaceTest de = new DefaultIntefaceTest() {
       // @Override
       // public void come() {
       //     System.out.println("haohao");
       // }
   };

    public static void main(String[] args) {
        de.f();
    }
}
