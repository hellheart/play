package com.huige.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dish {
    private  String name;

    private  boolean vegetarian;

    private  int calories;

    private  Type type;

    public enum Type { MEAT, FISH, OTHER }

    public enum CaloricLevel { DIET, NORMAL, FAT }
}
