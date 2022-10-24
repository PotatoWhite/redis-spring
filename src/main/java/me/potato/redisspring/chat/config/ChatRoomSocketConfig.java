package me.potato.redisspring.chat.config;

import lombok.RequiredArgsConstructor;
import me.potato.redisspring.chat.service.ChatRoomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class ChatRoomSocketConfig {
    private final ChatRoomService chatRoomService;

    @Bean
    public HandlerMapping handlerMapping() {
        var routeMap = Map.of(
                "/chat", chatRoomService
        );

        return new SimpleUrlHandlerMapping(routeMap, -1);
    }

}
