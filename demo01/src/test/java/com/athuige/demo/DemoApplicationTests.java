package com.athuige.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("时间是真正的结束了这是个整整的问题妈，这个世界已经没眷恋的意义");
    }


    @Test
    public void testObject() throws NoSuchFieldException, IllegalAccessException {
        String aa = new String("aa");
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        Set<MM> hashSet = new HashSet<>();
        MM a = new MM("a", 100);
        // MM a1 = new MM("a", 100);
        // hashSet.add(a);
        // hashSet.add(a1);
        // System.out.println("hash的大小为：" + hashSet.size());
        Class<? extends MM> aClass = a.getClass();
        Field field = null;
        field = aClass.getDeclaredField("num");
        field.setAccessible(true);
        field.set(a, "wo");
        System.out.println("a.getNum() = " + a.getNum());
    }
}
