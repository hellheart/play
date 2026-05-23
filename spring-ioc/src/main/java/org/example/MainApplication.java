package org.example;

import org.example.dao.UserDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.retry.annotation.EnableRetry;

import java.util.List;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"org.example"})
@MapperScan(basePackages = {"org.example.dao"})
@EnableRetry
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class);
    }
}