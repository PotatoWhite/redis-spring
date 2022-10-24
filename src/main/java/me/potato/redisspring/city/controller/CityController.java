package me.potato.redisspring.city.controller;

import lombok.RequiredArgsConstructor;
import me.potato.redisspring.city.dto.City;
import me.potato.redisspring.city.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("city")
public class CityController {
    private final CityService cityService;

    @GetMapping("{zipCode}")
    public Mono<City> getCity(@PathVariable String zipCode) {
        return this.cityService.getCity(zipCode);
    }

}
