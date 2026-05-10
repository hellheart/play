package com.huige.JVM;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class JplTest {

    public static void main(String[] args) {
        // System.out.println("VM.current().details() = " + VM.current().details());
        // Object r = new Object();
        // System.out.println("ClassLayout.parseInstance(r).toPrintable() = " + ClassLayout.parseInstance(r).toPrintable());
        Hello hello = new Hello();
        Member member = new Member("2mmmmmmmmmmmmmmmmmmmm", 2);
        // System.out.println("ClassLayout.parseInstance(r).toPrintable() = " + ClassLayout.parseInstance(hello).toPrintable());
        System.out.println("ClassLayout.parseInstance(r).toPrintable() = " + ClassLayout.parseInstance(member).toPrintable());
        System.out.println("");
    }
}

@Data
class Hello {
 int age;
 int[] s;
 Member member;
 Member member1;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Member {
    String a;
    Integer b;
}