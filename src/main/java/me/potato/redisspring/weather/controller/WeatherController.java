package me.potato.redisspring.weather.controller;

import lombok.RequiredArgsConstructor;
import me.potato.redisspring.weather.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("weather")
public class WeatherController {
    private final WeatherService service;

    @GetMapping("{zip}")
    public Mono<Integer> getWeather(@PathVariable int zip) {
        return Mono.fromSupplier(() -> service.getInfo(zip));
    }
}
