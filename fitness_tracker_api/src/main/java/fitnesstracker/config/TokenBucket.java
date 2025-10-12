package fitnesstracker.config;

public class TokenBucket {

    public static final long NANOSECONDS_IN_SECOND = 1_000_000_000;
    private final long capacity;
    private final long refillRatePerSecond;
    private long availableTokens;
    private long lastRefillTimestamp;

    public TokenBucket(long capacity, long refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.availableTokens = capacity;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized boolean tryConsume() {
        refill();
        if (availableTokens > 0) {
            availableTokens--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsedTime = now - lastRefillTimestamp;
        long tokensToAdd = (elapsedTime * refillRatePerSecond) / NANOSECONDS_IN_SECOND;

        if (tokensToAdd > 0) {  // Only update if we actually added tokens!
            availableTokens = Math.min(capacity, availableTokens + tokensToAdd);
            lastRefillTimestamp = now;
        }
    }
}
