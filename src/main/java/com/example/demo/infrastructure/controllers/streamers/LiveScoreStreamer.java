package com.example.demo.infrastructure.controllers.streamers;

import com.example.demo.domain.LiveScore;
import com.example.demo.use_cases.GetLiveScore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class LiveScoreStreamer {

    private static final int INTERVAL_PERIOD_IN_MILLIS = 1_000;
    private static final String LIVE_SCORE_EVENT_NAME = "live-score";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final GetLiveScore getLiveScore;
    private final Clock clock;

    public LiveScoreStreamer(GetLiveScore getLiveScore, Clock clock) {
        this.getLiveScore = getLiveScore;
        this.clock = clock;
    }

    public Flux<ServerSentEvent<LiveScoreEvent>> getLive() {
        AtomicReference<LiveScore> lastRecordedValue = new AtomicReference<>(null);
        return Flux.interval(Duration.ofMillis(INTERVAL_PERIOD_IN_MILLIS))
                .map(tick -> {
                    try {
                        LiveScore newValue = getLiveScore.execute();
                        String newValueAsJson = OBJECT_MAPPER.writeValueAsString(newValue);
                        String lastRecordedValueAsJson = OBJECT_MAPPER.writeValueAsString(lastRecordedValue.get());

                        if (newValueAsJson.equals(lastRecordedValueAsJson)) {
                            return keepAliveEvent();
                        } else {
                            lastRecordedValue.set(newValue);
                            return liveEvent(newValue);
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private ServerSentEvent<LiveScoreEvent> keepAliveEvent() {
        return ServerSentEvent.<LiveScoreEvent>builder().comment("keep-alive").event(LIVE_SCORE_EVENT_NAME).build();
    }

    private ServerSentEvent<LiveScoreEvent> liveEvent(LiveScore data) {
        LiveScoreEvent liveScoreEvent = new LiveScoreEvent(data, clock.millis());
        return ServerSentEvent.<LiveScoreEvent>builder().data(liveScoreEvent).event(LIVE_SCORE_EVENT_NAME).build();
    }
}
