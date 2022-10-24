package me.potato.redisspring.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    // have a strategy for cache eviction
    @Cacheable(value = "math:fib", key = "#index")
    public int getFib(int index) {
        System.out.println("calculating fib(" + index + ")");
        return this.fib(index);
    }

    // put, post, delete, patch
    @CacheEvict(value = "math:fib", key = "#index")
    public void clearCache(int index) {
        System.out.println("clearing cache key" + index);
    }

//    @Scheduled(fixedRate = 10_000)
    @CacheEvict(value = "math:fib", allEntries = true)
    public void clearCache() {
        System.out.println("clearing all fib keys");
    }

    // intentional 2^n
    private int fib(int index) {
        if (index < 2)
            return index;
        return fib(index - 1) + fib(index - 2);
    }
}
