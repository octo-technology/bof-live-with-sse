package com.example.demo.infrastructure.controllers.streamers;

import com.example.demo.domain.LiveScore;
import com.example.demo.use_cases.GetLiveScore;
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
                    LiveScore newValue = getLiveScore.execute();

                    lastRecordedValue.set(newValue);
                    return liveEvent(newValue);
                });
    }

    private ServerSentEvent<LiveScoreEvent> liveEvent(LiveScore data) {
        LiveScoreEvent liveScoreEvent = new LiveScoreEvent(data, clock.millis());
        return ServerSentEvent.<LiveScoreEvent>builder().data(liveScoreEvent).event(LIVE_SCORE_EVENT_NAME).build();
    }

}
