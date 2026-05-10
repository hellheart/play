package com.huige.virtureMachine.heap;

import lombok.Data;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Data
public class UUU {
    private String age;

    private List<AAA> aa;

    public static final ExecutorService e = new ThreadPoolExecutor(10, 10, 1, TimeUnit.MINUTES, new SynchronousQueue<>());

    @Data
    static class AAA {
        private String code;
    }

    public static void main(String[] args) throws Exception {
        // System.out.println(sendAllMail());
        // System.out.println("邮件收到完全");
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("a", 1);
        Map<String, List<String>> stringListHashMap = new HashMap<>();
        List<String> strings =
                Arrays.asList("1", "2", "3");
        stringListHashMap.put("sn", strings);
        List<String> sn = stringListHashMap.get("sn");
        List<String> strings1 = new ArrayList<>(sn);
        for (int i = 0; i < 3; i++) {
            Iterator<String> iterator = sn.iterator();
            if (iterator.hasNext()) {
                String next = iterator.next();
                strings1.remove(next);
                // sn = new ArrayList<>(strings1);
                sn = strings1;
                System.out.println("next = " + next);
                System.out.println("sn = " + sn);
                System.out.println("strings1 = " + strings1);
            }
        }

        // Iterator<String> iterator = sn.iterator();
        // String next = iterator.next();
        // strings1.remove(next);
        // System.out.println("s.remove(0) = " + strings1);
        // System.out.println("s.remove(0) = " + sn);


        // e.shutdown();
        List<String> strings2 = Arrays.asList("D:\\defend\\1.dat", "D:\\defend\\3.dat", "D:\\defend\\2.dat");
        for (String s3 : strings2) {
            try (FileWriter writer = new FileWriter(s3)) {
                writer.write("");// 清空原文件内容
                writer.write(s3);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        UUU.zip(new File("D:\\defend\\aaa.zip"), new File("D:\\defend\\1.dat"), new File("D:\\defend\\2.dat"));

    }

    private static void recursionCompress(ZipOutputStream zos, File srcFile, String zipEntryName) throws Exception {

        // 如果是目录，则创建目录并递归压缩子文件
        if (srcFile.isDirectory()) {
            // 添加目录
            zos.putNextEntry(new ZipEntry(zipEntryName + File.separator));
            zos.closeEntry();

            // 遍历并压缩子文件
            for (File subFile : srcFile.listFiles()) {
                String subZipEntryName = zipEntryName + File.separator + subFile.getName();
                recursionCompress(zos, subFile, subZipEntryName);
            }

            return;
        }

        // 如果是文件，则压缩文件
        zos.putNextEntry(new ZipEntry(zipEntryName));

        try (FileInputStream inputStream = new FileInputStream(srcFile)) {

            int len;
            byte[] buf = new byte[2 * 1024];

            while ((len = inputStream.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }

        } catch (Exception e) {
            throw e;
        }

        zos.closeEntry();
    }

    public static void zip(File destFile, File... srcFiles) throws Exception {

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destFile))) {

            for (File file : srcFiles) {
                if (file == null || !file.exists()) {
                    continue;
                }

                recursionCompress(zos, file, file.getName());
            }

        } catch (Exception e) {
            throw e;
        }
    }


    private static int sendAllMail() {
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CompletableFuture<String> booleanCompletableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                return sendMail();
            }, e);
            futureList.add(booleanCompletableFuture);
        }

        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();

        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<String> succs = new ArrayList<>();
        List<String> dail = new ArrayList<>();
        futureList.forEach(future -> {
            try {
                if ("1".equals(future.get(1000l, TimeUnit.MILLISECONDS))) {
                    atomicInteger.addAndGet(1);
                    System.out.println("取到值了");
                    succs.add(future.get(1000l, TimeUnit.MILLISECONDS));
                } else {
                    dail.add(future.get(1000l, TimeUnit.MILLISECONDS));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException ex) {
                throw new RuntimeException(ex);
            }
        });
        System.out.println("succs = " + succs);
        System.out.println("dail = " + dail);
        return atomicInteger.get();

    }


    public static String sendMail() {
        if (new Random().nextBoolean()) {
            System.out.println("发送邮件成功");
            return "1";
        } else {
            System.out.println("发送邮件失败");
            return "2";
        }
    }
}
