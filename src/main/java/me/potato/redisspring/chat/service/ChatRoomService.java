package me.potato.redisspring.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RListReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService implements WebSocketHandler {

    private final RedissonReactiveClient client;


    @Override
    public Mono<Void> handle(WebSocketSession session) {
        var                   room    = getChatRoomName(session);
        var                   topic   = this.client.getTopic(room, StringCodec.INSTANCE);
        RListReactive<String> history = client.getList("history:" + room, StringCodec.INSTANCE);

        // subscribe
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(msg -> history.add(msg).then(topic.publish(msg)))
                .doOnError(e -> log.error("error", e))
                .doFinally(s -> log.info("subscriber finally {} ", s))
                .subscribe();

        // publish
        var flux = topic.getMessages(String.class)
                .startWith(history.iterator())
                .map(session::textMessage)
                .doOnError(e -> log.error("error", e))
                .doFinally(s -> log.info("publisher finally {} ", s));

        return session.send(flux);
    }

    private String getChatRoomName(WebSocketSession session) {
        var uri = session.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .toSingleValueMap()
                .getOrDefault("room", "default");
    }
}
