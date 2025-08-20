package com.example.ticketing.inventory.web.sse;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class SeatUpdateBroadcaster {
    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    public void publish(String json) {
        sink.tryEmitNext(json);
    }

    public Flux<ServerSentEvent<String>> stream() {
        return sink.asFlux().map(data -> ServerSentEvent.builder(data).build());
    }
}
