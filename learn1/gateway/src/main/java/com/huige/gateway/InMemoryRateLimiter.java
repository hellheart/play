package com.huige.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryRateLimiter extends AbstractRateLimiter<InMemoryRateLimiter.Config> {

    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    public InMemoryRateLimiter() {
        super(Config.class, "in-memory-rate-limiter", null);
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        Config config = getConfig().get(routeId);
        int replenishRate = config == null ? 10 : config.getReplenishRate();
        int burstCapacity = config == null ? 20 : config.getBurstCapacity();

        String bucketKey = routeId + ":" + id;
        TokenBucket bucket = buckets.computeIfAbsent(bucketKey,
                k -> new TokenBucket(burstCapacity, replenishRate));
        boolean allowed = bucket.tryConsume();
        return Mono.just(new Response(allowed, new ConcurrentHashMap<>()));
    }

    public static class Config {
        private int replenishRate = 10;
        private int burstCapacity = 20;

        public int getReplenishRate() { return replenishRate; }
        public void setReplenishRate(int replenishRate) { this.replenishRate = replenishRate; }
        public int getBurstCapacity() { return burstCapacity; }
        public void setBurstCapacity(int burstCapacity) { this.burstCapacity = burstCapacity; }
    }

    private static class TokenBucket {
        private final int burstCapacity;
        private final double refillRate;
        private final AtomicInteger tokens;
        private final AtomicLong lastRefill;

        TokenBucket(int burstCapacity, int refillRate) {
            this.burstCapacity = burstCapacity;
            this.refillRate = refillRate / 1000.0;
            this.tokens = new AtomicInteger(burstCapacity);
            this.lastRefill = new AtomicLong(System.currentTimeMillis());
        }

        boolean tryConsume() {
            refill();
            while (true) {
                int current = tokens.get();
                if (current <= 0) return false;
                if (tokens.compareAndSet(current, current - 1)) return true;
            }
        }

        void refill() {
            long now = System.currentTimeMillis();
            long prev = lastRefill.get();
            long elapsed = now - prev;
            if (elapsed > 0) {
                int newTokens = (int) (elapsed * refillRate);
                if (newTokens > 0) {
                    if (lastRefill.compareAndSet(prev, now)) {
                        tokens.updateAndGet(t -> Math.min(burstCapacity, t + newTokens));
                    }
                }
            }
        }
    }
}
