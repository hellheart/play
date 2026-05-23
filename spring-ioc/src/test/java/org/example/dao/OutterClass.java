package org.example.dao;

import java.util.function.Function;

public class OutterClass implements MyInter{
    private Integer age = 100;

    @Override
    public String plus(String name) {
        return name + "1";
    }

    class Innner {
        private String name;

        private Integer getAny() {
            return OutterClass.this.age;
        }


    }


    public static void main(String[] args) {
        OutterClass outterClass = new OutterClass();
        Innner innner = outterClass.new Innner();
        Integer any = innner.getAny();
        String name = innner.name;
        System.out.println("name = " + name);
        System.out.println("any = " + any);
        OutterClass outterClass1 = new OutterClass();
        MyInter stringStringFunction = outterClass1::plus;
        System.out.println(stringStringFunction.plus("aaaa"));

    }
}
