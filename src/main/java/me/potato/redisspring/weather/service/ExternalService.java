package me.potato.redisspring.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;


@Slf4j
@RequiredArgsConstructor
@Service
public class ExternalService {

    @CachePut(value = "weather", key = "#zip")
    public int getWeatherInfo(int zip){
        var temp = ThreadLocalRandom.current().nextInt(0, 40);
        log.info("getting weather info for zip {} : {}", zip, temp);
        return temp;
    }
}
