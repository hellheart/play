package com.huige.gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/user")
    public Map<String, Object> userFallback() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 503);
        result.put("message", "用户服务暂时不可用，请稍后重试");
        result.put("content", new Object[0]);
        result.put("totalElements", 0);
        result.put("totalPages", 0);
        return result;
    }

    @RequestMapping("/fallback/bilibili")
    public Map<String, Object> bilibiliFallback() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 503);
        result.put("message", "B站排行榜服务暂时不可用，请稍后重试");
        result.put("data", new HashMap<>());
        return result;
    }
}
