package com.huige.learning.factory.facmethod;

import com.huige.learning.factory.simple.Apple;
import com.huige.learning.factory.simple.Banana;
import com.huige.learning.factory.simple.Fruit;

public class FactoryApp {

    public static void main(String[] args) {
        Apple1 apple1 = new Apple1();
        Apple fruit = apple1.createFruit();
        Banana1 banana1 = new Banana1();
        Banana fruit1 = banana1.createFruit();
        fruit.getType();
        fruit1.getType();

    }
}
