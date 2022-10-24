package me.potato.redisspring;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class ApplicationTests {

//    @Autowired
//    private ReactiveStringRedisTemplate template;

    @Autowired
    private RedissonReactiveClient client;

//    @RepeatedTest(3)
//    @Test
//    void springDataRedisTest() {
//
//        long before = System.currentTimeMillis();
//
//        ReactiveValueOperations<String, String> valueOperations = template.opsForValue();
//        Mono<Void> mono = Flux.range(1, 500_000)
//                .flatMap(i -> valueOperations.increment("user:1:visit")) //incr
//                .then();
//
//        StepVerifier.create(mono)
//                .verifyComplete();
//
//        System.out.println("Elapsed: " + (System.currentTimeMillis() - before));
//    }

    @RepeatedTest(3)
    @Test
    void redissonTest() {

        long before = System.currentTimeMillis();

        RAtomicLongReactive atomicLong = client.getAtomicLong("user:2:visit");
        Mono<Void> mono = Flux.range(1, 500_000)
                .flatMap(i -> atomicLong.incrementAndGet()) //incr
                .then();

        StepVerifier.create(mono)
                .verifyComplete();

        System.out.println("Elapsed: " + (System.currentTimeMillis() - before));
    }

}
