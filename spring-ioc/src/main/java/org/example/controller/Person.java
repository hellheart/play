package org.example.controller;

public class Person {
    public static final int O = BloodGroup.O.getCode();
    public static final int A = BloodGroup.A.getCode();
    public static final int B = BloodGroup.B.getCode();
    public static final int AB = BloodGroup.AB.getCode();
    private BloodGroup _bloodGroup;


    public BloodGroup getBloodGroup() {
        return _bloodGroup;
    }

    public Person(BloodGroup bloodGroup) {
        _bloodGroup = bloodGroup;
    }

    public void setBloodGroup(BloodGroup arg) {
        _bloodGroup = arg;
    }
}