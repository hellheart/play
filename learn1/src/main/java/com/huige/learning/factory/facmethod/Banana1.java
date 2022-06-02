package com.huige.learning.factory.facmethod;

import com.huige.learning.factory.simple.Banana;
import com.huige.learning.factory.simple.Fruit;

public class Banana1 implements FactoryFruit {
    @Override
    public Banana createFruit() {
        return new Banana();
    }
}
