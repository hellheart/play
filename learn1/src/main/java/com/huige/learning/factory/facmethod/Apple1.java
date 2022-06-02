package com.huige.learning.factory.facmethod;

import com.huige.learning.factory.simple.Apple;
import com.huige.learning.factory.simple.Fruit;

public class Apple1 implements FactoryFruit {
    @Override
    public Apple createFruit() {
        return new Apple();
    }
}
