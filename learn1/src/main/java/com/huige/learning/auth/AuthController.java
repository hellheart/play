package com.huige.learning.auth;

import com.huige.learning.config.JwtUtil;
import com.huige.learning.config.TokenBlacklist;
import com.huige.learning.user.User;
import com.huige.learning.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> result = new HashMap<>();
        User user = userMapper.findByUsername(username);
        if (user == null || !password.equals(user.getPassword())) {
            result.put("code", 401);
            result.put("message", "用户名或密码错误");
            return result;
        }
        String token = JwtUtil.generateToken(username);
        result.put("code", 200);
        result.put("message", "登录成功");
        result.put("token", token);
        user.setPassword(null);
        result.put("user", user);
        return result;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        User exist = userMapper.findByUsername(user.getUsername());
        if (exist != null) {
            result.put("code", 400);
            result.put("message", "用户名已存在");
            return result;
        }
        userMapper.insert(user);
        result.put("code", 200);
        result.put("message", "注册成功");
        return result;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> result = new HashMap<>();
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklist.add(token);
        }
        result.put("code", 200);
        result.put("message", "登出成功");
        return result;
    }
}
