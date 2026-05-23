package org.example.dao.defaults;

import org.example.controller.Hello;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultTest  implements DefaultA,DefaultC{

    public void hello() {
        DefaultA.super.hello();
    }

    public static void main(String[] args) {
        DefaultTest defaultTest = new DefaultTest();
        defaultTest.hello();

        Properties properties = new Properties();
        properties.setProperty("a1", "100");
        properties.setProperty("b", "asda");
        properties.setProperty("a", "10");

        int value = getNUm(properties, "a");
        System.out.println("value = " + value);

        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("i = " + i);
        StopWatch task1 = new StopWatch("task1");
        task1.start();
        List<String> strings = Arrays.asList("dada", "dadadadada", "ffsdfdfsdf");
        // List<String> collect = strings.stream().map(DefaultTest::getPrice).map(DefaultTest::getPriceName).map(DefaultTest::getTotal).collect(Collectors.toList());
        // 第二种
        CompletableFuture[] completableFutures = strings.stream().map(string1 -> CompletableFuture.supplyAsync(() -> DefaultTest.getPrice(string1))
                        .exceptionally(e -> {
                            System.out.println(e.getLocalizedMessage());
            return 20;
                }))
                .map(future -> future.thenApply(DefaultTest::getPriceName))
                .map(futur -> futur.thenCompose(qt -> CompletableFuture.supplyAsync(() -> DefaultTest.getTotal(qt)).exceptionally(e -> {
                    System.out.println(e.getLocalizedMessage() + "再次");
                    return String.valueOf(2);
                })))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        for (CompletableFuture completableFuture : completableFutures) {
            String join = (String)completableFuture.join();
            System.out.println("join = " + join);
        }
        //
        task1.stop();
        System.out.println(task1.prettyPrint());

    }

    private static int getNUm(Properties properties, String a) {
        return Optional.ofNullable(properties.getProperty(a)).flatMap(DefaultTest::stringtoInt).filter(i -> i > 0).orElse(0);
    }

    private static Optional<Integer> stringtoInt( String a) {
        try {
            return Optional.of(Integer.parseInt(a));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static void getRandom() {
        Random random = new Random(47);
        int delay = random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getPrice(String any) {
        getRandom();
        int tt = 10 / 0;
        Random random = new Random(47);
        return any.length() + random.nextInt(20);
    }

    private static String getPriceName(Integer price) {
        return price.toString();
    }

    private  static String getTotal(String name) {
      String re =   getPrice(name) + getPriceName(20);
        System.out.println("re = " + re);
        return  re;
    }

    @Test
    public void testColihua() {
        Function<String, Function<String, Function<String, String>>> stringFunctionFunction = grotoNum1();
        Function<String, Function<String, String>> apply = stringFunctionFunction.apply("2");
        Function<String, String> apply1 = apply.apply("3");
        String apply2 = apply1.apply("6");
        System.out.println("apply2 = " + apply2);
    }

    private String grotoNum(Integer i1, Integer i2, Double d1, Double d2, String s1, String s2) {
        return i1 + i2 + d1 + d2 + s1 + s2;
    }

    // 柯里化是将函数表达式作为参数传递

    private Function<String, Function<String, Function<String, String>>> grotoNum1() {

        return a -> b -> c ->a ;
    }

    private MyInterFace<Integer, Double, MyInterFace1<Integer, MyInterFace1<Integer, MyInterFace2<Double, String>>>> grotoNum3() {
        return (a, b ,c) -> (d) -> (e, f) -> e + f + a + b + c + d;
    }

    private BiFunction<String, String, BiFunction<String, String, BiFunction<String, String, String>>> grotoNum2() {

        return (a, b) -> (c, d) -> (f, e) -> a ;
    }

    interface MyInterFace<I, D, S> {
        S acounttt(I r, D q2, S m1);
    }

    interface MyInterFace1<R,M> {
        MyInterFace2<R, M> acounttt1(M m1);
    }

    interface MyInterFace2<I, S> {
        S acounttt2(I r1, S m1);
    }
}
