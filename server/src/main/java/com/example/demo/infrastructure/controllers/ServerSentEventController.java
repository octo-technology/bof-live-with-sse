package com.example.demo.infrastructure.controllers;

import com.example.demo.infrastructure.controllers.streamers.LiveScoreEvent;
import com.example.demo.infrastructure.controllers.streamers.LiveScoreStreamer;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
public class ServerSentEventController {

    private final LiveScoreStreamer liveScoreStreamer;

    public ServerSentEventController(LiveScoreStreamer liveScoreStreamer) {
        this.liveScoreStreamer = liveScoreStreamer;
    }

    @GetMapping(value = "/api/sse", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent> getServerSentEvents() {
        Flux<ServerSentEvent<LiveScoreEvent>> liveFlux = liveScoreStreamer.getLive();
        return Flux.merge(liveFlux);
    }
}
