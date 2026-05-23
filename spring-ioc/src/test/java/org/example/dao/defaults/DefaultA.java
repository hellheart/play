package org.example.dao.defaults;

public interface DefaultA {
    default void hello() {
        System.out.println("from DefaultA");
    }
}
