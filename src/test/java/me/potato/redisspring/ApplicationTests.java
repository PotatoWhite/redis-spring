package me.potato.redisspring;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private ReactiveStringRedisTemplate template;

    @RepeatedTest(3)
    @Test
    void springDataRedisTest() {

        long before = System.currentTimeMillis();

        ReactiveValueOperations<String, String> valueOperations = template.opsForValue();
        Mono<Void> mono = Flux.range(1, 500_000)
                .flatMap(i -> valueOperations.increment("user:1:visit")) //incr
                .then();

        StepVerifier.create(mono)
                .verifyComplete();

        System.out.println("Elapsed: " + (System.currentTimeMillis() - before));
    }

}
