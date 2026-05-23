package org.example.controller;

import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("qq")
    public List<Map<String, Object>> getAny(String a, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            String key = stringEntry.getKey();
            String[] value = stringEntry.getValue();
        }
        List<Map<String, Object>> run = userService.run();
        System.out.println("run = " + run);
        return run;
    }
}
