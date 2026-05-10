package com.huige.learning.config;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBlacklist {

    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    public void add(String token) {
        try {
            Claims claims = JwtUtil.parseToken(token);
            Date exp = claims.getExpiration();
            if (exp != null) {
                blacklist.put(token, exp.getTime());
            }
        } catch (Exception ignored) {
        }
    }

    public boolean isBlacklisted(String token) {
        cleanup();
        return blacklist.containsKey(token);
    }

    private void cleanup() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, Long>> it = blacklist.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue() < now) {
                it.remove();
            }
        }
    }
}
