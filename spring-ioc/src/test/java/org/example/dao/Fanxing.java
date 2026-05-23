package org.example.dao;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Fanxing {
    public static void main(String[] args) {
        String[] sprefix = {"a","d","e","t","q"};
        Arrays.parallelPrefix(sprefix, (a1, b1) -> a1 + b1);
        System.out.println(Arrays.toString(sprefix));

        Arrays.fill(sprefix, "1");
        System.out.println(Arrays.toString(sprefix));

        Arrays.setAll(sprefix, n -> sprefix[n] + n);
        System.out.println(Arrays.toString(sprefix));
    }

    void test(Pass p) {
        Pass pass = p.get();
        PassTest pass1 = p.get();
    }
}

interface Pass extends  PassTest<Pass> {}

interface PassTest<T extends PassTest<T>> {
    T get();
}


