package me.potato.redisspring.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final ExternalService client;

    @Cacheable(value = "weather", key = "#zip")
    public int getInfo(int zip){
        return 0;
    }

    @Scheduled(fixedRate = 10_000)
    public void update(){
        IntStream.rangeClosed(1, 5)
                .forEach(client::getWeatherInfo);
    }

}
