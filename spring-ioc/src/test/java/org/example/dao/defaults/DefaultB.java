package org.example.dao.defaults;

public interface DefaultB {
    default void hello() {
        System.out.println("from DefaultB");
    }
}
