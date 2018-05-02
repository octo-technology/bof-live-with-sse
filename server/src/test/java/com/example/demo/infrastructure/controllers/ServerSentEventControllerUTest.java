package com.example.demo.infrastructure.controllers;

import com.example.demo.MockitoExtension;
import com.example.demo.domain.LiveScore;
import com.example.demo.infrastructure.controllers.streamers.LiveScoreEvent;
import com.example.demo.infrastructure.controllers.streamers.LiveScoreStreamer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServerSentEventControllerUTest {

    private ServerSentEventController serverSentEventController;

    @Mock
    private LiveScoreStreamer liveScoreStreamer;

    @BeforeEach
    void setUp() {
        serverSentEventController = new ServerSentEventController(liveScoreStreamer);
    }

    @Nested
    class GetServerSentEventsShould {

        @Test
        void return_flux_containing_live_events() {
            // given
            ServerSentEvent<LiveScoreEvent> liveEvent = ServerSentEvent.<LiveScoreEvent>builder()
                    .data(new LiveScoreEvent(mock(LiveScore.class), null))
                    .event("live")
                    .build();
            Flux<ServerSentEvent<LiveScoreEvent>> liveFlux = Flux.just(liveEvent);
            when(liveScoreStreamer.getLive()).thenReturn(liveFlux);

            // when
            Flux<ServerSentEvent> result = serverSentEventController.getServerSentEvents();

            // then
            StepVerifier.create(result)
                    .expectNext(liveEvent)
                    .thenCancel().verify();
        }
    }
}
