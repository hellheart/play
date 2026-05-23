package org.example.service.impl;


import org.example.dao.UserDao;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

@Autowired
private UserDao userDao;

@Autowired
private UserService userService;
    @Override

    public List<Map<String, Object>> run() {
        System.out.println("dadadad");
        userService.retrySucc();
     return userDao.run();
    }

    private  List<Map<String, Object>> hello2() {
        System.out.println("dadadad");
        userService.retrySucc();;
        return userDao.run();
    }

    private  List<Map<String, Object>> hello1() {
       return hello2();
    }

    @Override
    public List<Map<String, Object>> hello() {
        return hello1();
    }

    @Retryable(value = {Exception.class})
    public void retrySucc() {
        System.out.println("重试机制能生效");
       // throw new RuntimeException();
    }

    public static void main(String[] args) {
        System.out.println("args = " + args);
    }
}
