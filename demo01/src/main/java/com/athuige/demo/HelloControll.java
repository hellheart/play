package com.athuige.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloControll {
    @Autowired
    private HtmlToPdfService htmlToPdfService;

    @RequestMapping("/me")
    public String wplll(HttpServletRequest request) throws IOException {
        log.info("当前线程: {}, 请求：{}", Thread.currentThread().getName(), request);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "wplll");
        hashMap.put("age", 18);
        htmlToPdfService.transTohtml(hashMap);
        return "me";
    }
}
