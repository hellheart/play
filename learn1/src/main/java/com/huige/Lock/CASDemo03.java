package com.huige.Lock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@AllArgsConstructor
class Book {
    private String name;

    private Integer age;
}

public class CASDemo03 {
    public static void main(String[] args) {
        Book lisi = new Book("lisi", 20);
        Book z3 = new Book("z3", 20);
        AtomicReference<Book> bookAtomicReference = new AtomicReference<>();
        new Thread(() -> {
            bookAtomicReference.compareAndSet(lisi, z3);
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
            System.out.println("bookAtomicReference = " + bookAtomicReference.get());
            bookAtomicReference.compareAndSet(z3, lisi);
            System.out.println("bookAtomicReference = " + bookAtomicReference.get());
            bookAtomicReference.compareAndSet(lisi, z3);
        }, "t1").start();

        new Thread(() -> {

        }, "t2").start();
    }
}
