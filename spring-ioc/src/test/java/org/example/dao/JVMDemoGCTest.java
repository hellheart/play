package org.example.dao;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JVMDemoGCTest {
    public static void main(String[] args) {
        // List<Object> objects = new ArrayList<>();
        // for (int i = 0; i < 500; i++) {
        //     byte[] bytes = new byte[1024 * 1024];
        //     objects.add(bytes);
        //     try {
        //         Thread.sleep(50l);
        //     } catch (InterruptedException e) {
        //         throw new RuntimeException(e);
        //     }
        // }
        // byte a=1;char b=2;
        // int i = a + b;
        List<String> strings = Arrays.asList("a", "b", "c");
        outter:
        for (String string : strings) {

            System.out.println("string = " + string);
            inner:
            for (String s : strings) {
                if (s.equals("a")) {
                    System.out.println("s = " + s);
                    continue outter;
                }
                if (s.equals("c")) {
                    System.out.println("break");
                    break inner;
                }
            }
        }
        System.out.println("tiaochulaile");
    }

    static int[] ints = new Random(100).ints(10, 100).limit(10).toArray();

    @Test
    public void testStreamBox() {
        Random random = new Random(47);
        random.ints(10,20).boxed().distinct().limit(7).forEach(System.out::println);
        System.out.println(IntStream.range(20, 100).sum());
        Stream.generate(() -> "hello").limit(7).forEach(System.out::println);
        Stream.generate(() -> new Random(47).ints(10, 20).boxed().limit(1).findFirst().orElse(100))
                .limit(5)
                .peek(System.out::println).reduce((a, b) -> a+ b).ifPresent(System.out::println);
        String any = null;
        String aNull = Optional.ofNullable(any).orElse("null");
        System.out.println(aNull);
        System.out.println(Arrays.stream(ints));
        System.out.println(Arrays.stream(ints));

        String aaa = "22256660";
        BufferedReader bufferedInputStream = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("D:\\hello.txt");
            bufferedInputStream = new BufferedReader((new FileReader("C:\\Users\\yh\\Desktop\\操作数栈与局部变量表篇.txt")));
            int a = 0;
            while ((a = bufferedInputStream.read()) != -1) {
                fileWriter.write(a);
                fileWriter.flush();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                    fileWriter.close();
                } catch (IOException e) {
                    }
                }
            }
        }
        @Test
    public void testBenchMark() throws IOException {
            Path test = Paths.get("D:\\test20220601");
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{java,class}");
            Files.walk(test).filter(pathMatcher::matches).forEach(System.out::println);
        }

        @Test
    public void testStream() {
        
        }
    }

