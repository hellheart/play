package com.huige.learning.bilibili;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/bilibili")
public class BiliBiliController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/ranking")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRanking() {
        return callBiliApi("https://api.bilibili.com/x/web-interface/ranking/v2?rid=0&type=all");
    }

    @GetMapping("/up-ranking")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUpRanking() {
        return callBiliApi("https://api.bilibili.com/x/web-interface/popular?pn=1&ps=50");
    }

    private ResponseEntity<Map<String, Object>> callBiliApi(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            headers.set("Referer", "https://www.bilibili.com/");
            headers.set("Cookie", "buvid3=auto");

            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            String body = resp.getBody();
            if (body == null) {
                Map<String, Object> err = new HashMap<>();
                err.put("code", -1);
                err.put("message", "empty response");
                err.put("data", new HashMap<>());
                return ResponseEntity.ok(err);
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> biliResp = objectMapper.readValue(body, Map.class);
            return ResponseEntity.ok(biliResp);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", -1);
            error.put("message", "获取失败: " + e.getMessage());
            error.put("data", new HashMap<>());
            return ResponseEntity.ok(error);
        }
    }
}
