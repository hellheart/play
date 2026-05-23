package com.athuige.demo;

import java.util.Objects;

public class MM {
    private String num;

    private int anInt;

    public MM(String num, int anInt) {
        this.num = num;
        this.anInt = anInt;
    }

    public MM() {
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, anInt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MM mm = (MM) o;
        return anInt == mm.anInt && Objects.equals(num, mm.num);
    }

    @Override
    public String toString() {
        return "MM{" +
                "num='" + num + '\'' +
                ", anInt=" + anInt +
                '}';
    }
}
