package org.example.clazz;


import java.util.Random;

public class FoodTest {

    public Food randomFood(Class<? extends Food> food) {
        return getRamdom(food.getEnumConstants());
    }

    private Food getRamdom(Food[] enumConstants) {
        Random random = new Random();
        return enumConstants[random.nextInt(enumConstants.length)];
    }

    public static void main(String[] args) {
        FoodTest foodTest = new FoodTest();
        Food food = foodTest.randomFood(Food.Tea.class);
        System.out.println("food = " + food);
    }
}
