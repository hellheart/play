package org.example.dao;

import java.util.function.Function;


class A {
    @Override
    public String toString() {
        return "A";
    }
}

class B {
    @Override
    public String toString() {
        return "B";
    }
}

public class Transform {
    static Function<A, B> trans(Function<A, B> in) {
        return in.andThen(o ->
        {
            System.out.println("o = " + o);
            return o;
        });
    }

    public static void main(String[] args) {
        Function<A, B> trans = Transform.trans(i -> {
            System.out.println(i);
            return new B();
        });
        B apply = trans.apply(new A());
    }
}
