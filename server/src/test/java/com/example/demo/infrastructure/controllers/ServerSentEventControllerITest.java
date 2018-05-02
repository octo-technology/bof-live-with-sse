package com.example.demo.infrastructure.controllers;

import com.example.demo.infrastructure.controllers.streamers.LiveScoreEvent;
import com.example.demo.infrastructure.controllers.streamers.LiveScoreStreamer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ServerSentEventController.class)
class ServerSentEventControllerITest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LiveScoreStreamer liveScoreStreamer;

    @Nested
    class GetOnSseEndpointShould {

        @Test
        void return_flux_containing_live_event_as_text_event_stream() throws Exception {
            // given
            ServerSentEvent<LiveScoreEvent> liveEvent = ServerSentEvent.<LiveScoreEvent>builder()
                    .data(new LiveScoreEvent(null, null))
                    .event("live-score")
                    .build();
            Flux<ServerSentEvent<LiveScoreEvent>> liveFlux = Flux.just(liveEvent);
            when(liveScoreStreamer.getLive()).thenReturn(liveFlux);

            // when
            ResultActions resultActions = mvc.perform(get("/api/sse"));
            Thread.sleep(200); // Wait for stream to be complete

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().string("event:live-score\n" +
                            "data:{\"liveScore\":null,\"eventTimestamp\":null}\n" +
                            "\n"))
                    .andExpect(header().string("Content-Type", "text/event-stream;charset=UTF-8"));
        }
    }
}
