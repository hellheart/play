package com.huige.learning.factory.simple;

public class FruitFactory {

    public Fruit getByType(String name) {
        if (name.equals("apple")) {
            return new Apple();
        } else if(name.equals("banana")) {
            return new Banana();
        }
        return null;
    }

    public static void main(String[] args) {
        FruitFactory fruitFactory = new FruitFactory();
        Fruit apple = fruitFactory.getByType("apple");
        apple.getType();
    }
}
