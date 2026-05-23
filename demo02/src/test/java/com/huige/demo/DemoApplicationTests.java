package com.huige.demo;

import com.huige.demo.domain.Dish;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@SpringBootTest
@DisplayName("java8测试")
@Slf4j
class DemoApplicationTests {





    @Test
    void contextLoads() {
    }


    @Test
    @DisplayName("测试一")
    public void test1() {
        List<Dish> menu = getMenu();
        Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> typeMapMap = menu.stream()
                .collect(groupingBy(Dish::getType, groupingBy(dish -> {
                    if (dish.getCalories() > 400) {
                        return Dish.CaloricLevel.DIET;
                    } else if (dish.getCalories() > 700) {
                        return Dish.CaloricLevel.NORMAL;
                    }
                    else {
                        return Dish.CaloricLevel.FAT;
                    }
                    }
                )));
        log.info("分类的结果是：{}",typeMapMap);
    }



    public static List<Dish> getMenu() {
        
        return Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH) );
    }

    @Test
    public void testHashMap() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1", "神奇");
        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
    }
}
