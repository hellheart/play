package com.huige.virtureMachine.heap;

import co.paralleluniverse.concurrent.util.CompletableFutureTask;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class OOMTest {

    public static void main(String[] args) {
        //     List<Picture> pictures = new ArrayList<>();
        //     while (true) {
        //         try {
        //             Thread.sleep(20);
        //         } catch (Exception e) {
        //             throw new RuntimeException(e);
        //         }
        //         pictures.add(new Picture(new Random().nextInt(1024 * 1024)));
        //     }
        // }

        JSONArray objects = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("age", "100");

        jsonObject.put("name", "xiaoming");
        objects.add(jsonObject);

        List<UUU> uuus = JSONArray.parseArray(objects.toJSONString(), UUU.class);

        System.out.println("uuus = " + uuus);

        // JSONArray objects = new JSONArray();
        // JSONObject jsonObject1 = new JSONObject();
        // jsonObject1.put("code", "1321");
        // objects.add(jsonObject1);
        //
        //
        //
        // jsonObject.put("aa", objects);
        //
        // UUU uuu = JSONObject.parseObject(String.valueOf(jsonObject), UUU.class);
        //
        // List<UUU.AAA> aa = uuu.getAa();
        //
        // System.out.println("uuu.getAge() = " + uuu.getAge());
        // System.out.println("aa = " + aa);
        //
        // System.out.println("uuu = " + uuu);
        //
        // String json = jsonObject.toJSONString();
        //
        // UUU uuu1 = new UUU();

    }

    class Picture {
        private byte[] picSize;

        public Picture(int picSize) {
            this.picSize = new byte[picSize];
        }
    }
}