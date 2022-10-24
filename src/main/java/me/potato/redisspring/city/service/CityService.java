package me.potato.redisspring.city.service;

import me.potato.redisspring.city.client.CityClient;
import me.potato.redisspring.city.dto.City;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class CityService {
    private final CityClient                 cityClient;
    private final RMapReactive<String, City> cityMap;

    public CityService(CityClient cityClient, RedissonReactiveClient client) {
        this.cityClient = cityClient;
        this.cityMap    = client.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));
    }

    public Mono<City> getCity(String zipCode) {
        return this.cityMap.get(zipCode)
                .onErrorResume(ex -> this.cityClient.getCity(zipCode));
    }

//    @Scheduled(fixedRate = 10_000)
    public void updateCity() {
        this.cityClient.getAll()
                .collectList()
                .map(list -> list.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(this.cityMap::putAll)
                .subscribe();
    }
}
