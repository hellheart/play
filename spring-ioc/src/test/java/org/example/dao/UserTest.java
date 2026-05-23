package org.example.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
public class UserTest {

    @MockBean
    private UserDao userDao;

    @MockBean
    private UserService userService1;

    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(userDao.run()).thenReturn(new ArrayList<>());
    }

    @Test
    @DisplayName("测试初步打桩")
    public void test1() {
        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    @DisplayName("测试verify校验")
    public void test2() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        //when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed.
        verify(mockedList).get(0);


    }

    @Test
    @DisplayName("测试inOrder方法")
    public void test3() {
        LinkedList mock = mock(LinkedList.class);
        mock.add("aaa");
        mock.add("aaa");
        InOrder inOrder = inOrder(mock);
        when(mock.add(0)).thenReturn("aaaa".isEmpty());
        inOrder.verify(mock, atLeastOnce()).add("aaa");
        verify(mock, times(2)).add("aaa");
        //inOrder.verify(mock).add("aaa"); 只能测试一次操作
    }

    @Test
    @DisplayName("测试打桩异常方法")
    public void test4() {
        List mock = mock(List.class);
        for (int i = 0; i < 30; i++) {
            mock.add(i);
        }
        when(mock.get(anyInt())).thenReturn("add success");
        verify(mock).add(29);
    }

    @Test
    @DisplayName("测试打桩异常方法")
    public void test5() {
        List mock = mock(List.class);
        for (int i = 0; i < 30; i++) {
            mock.add(i);
        }
        when(mock.get(anyInt())).thenReturn("add success");
        verify(mock).add(29);
    }


    @Test
    @DisplayName("Finding redundant invocations")
    public void test6() {
        List mock = mock(List.class);
        mock.add("one");
        //mock.add("one1");
        verify(mock).add("one");
        verifyNoMoreInteractions(mock);
    }

    @Test
    @DisplayName("Finding redundant invocations")
    public void test7() {
        List mock = mock(List.class);
        mock.add("one");
        //mock.add("one1");
        verify(mock).add("one");
        verifyNoMoreInteractions(mock);
    }

    @Test
    public void test8() {
        when(userService.run()).thenReturn(new ArrayList<>());
        List<Map<String, Object>> run = userService.run();
        verify(userService).run();
    }

    @Test
    public void test9() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch("rewukaishi");
        stopWatch.start("task1");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("时间");
            return "时间";}).exceptionally(e -> "机器没坏继续");
        stopWatch.stop();
        stopWatch.start("task2");
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            int i = 1 /0;
            System.out.println("future = ");});
        // stopWatch.stop();
        // stopWatch.start("task3");
        // CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
        //     System.out.println("时间1");
        //     return "时间";}).exceptionally(e -> "机器没坏继续");
        CompletableFuture.allOf(future, voidCompletableFuture).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                return null;
            }
        });
        String s = future.get();
        voidCompletableFuture.get();
        stopWatch.stop();
        // String s1 = future1.get();
        // stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println("结束");
    }

    @Test
    public void test10() {
        when(userDao.run()).thenReturn(new ArrayList<>());
        List<Map<String, Object>> run = userService.run();
        Assert.notEmpty(run, "数据库不为空");
    }

    @Test
    public void testObserver() {
        Observable observable = new Observable();
    }

    @Test
    public void test12() {
        List<Map<String, Object>> hello = userService.hello();
        System.out.println(hello);
    }

    @Test
    public void testPath() throws IOException {
        Path workPath = Paths.get("D:\\test20220601\\demo");
        // Path parent = workPath.getParent();
        // Path fileName = workPath.getFileName();
        // Path root = workPath.getRoot();
        // log.info("workPath is {}, paernt is {}, filename is {}, root is {}", workPath, parent, fileName, root);
        //byte[] bytes = Files.readAllBytes(workPath);
       // String string = new String(bytes, Charset.defaultCharset());
        //List<String> strings = Files.readAllLines(workPath, Charset.defaultCharset());
        ///log.info("{},{},{}",string, strings);
        /**
         * 获取该目录i下的所有文件
         */
        try (Stream<Path> walk = Files.walk(workPath, FileVisitOption.values())){
                walk.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /**
         * 过滤文件可用
         */
        try{
            Files.find(workPath, 2, (path, basicFileAttributes) -> basicFileAttributes.isDirectory() && path.startsWith("\\"), FileVisitOption.FOLLOW_LINKS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /**
         * 读取zip压缩文件
         */
        try (FileSystem fileSystem = FileSystems.newFileSystem(Paths.get("C:\\Users\\yh\\Desktop\\su.zip"), null)){
            Files.walkFileTree(fileSystem.getPath("/"), new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    System.out.println(dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.out.println(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    System.out.println(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileChannel open = FileChannel.open(workPath, StandardOpenOption.values());
    }
}
