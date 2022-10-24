package me.potato.redisspring.fib.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;


@Configuration
public class RedissonCacheConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.username}")
    private String username;

    private RedissonClient redissonClient;

    @Bean
    public RedissonClient getClient(){
        if(Objects.isNull(this.redissonClient)) {
            var config = new Config();
            config.useSingleServer()
                    .setUsername(username)
                    .setPassword(password)
                    .setAddress("redis://" + host + ":" + port); //default port
            redissonClient = Redisson.create(config);
        }

        return redissonClient;
    }


    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        return  new RedissonSpringCacheManager(redissonClient);
    }
}
