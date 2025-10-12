package fitnesstracker.config;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    public boolean tryConsume(String apikey) {
        TokenBucket bucket = buckets.computeIfAbsent(apikey, k -> new TokenBucket(1, 1));
        return bucket.tryConsume();
    }
}
