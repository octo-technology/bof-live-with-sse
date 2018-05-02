package com.example.demo.infrastructure.controllers.streamers;

import com.example.demo.domain.LiveScore;

public class LiveScoreEvent {
    private LiveScore liveScore;
    private Long eventTimestamp;

    public LiveScoreEvent(LiveScore liveScore, Long eventTimestamp) {
        this.liveScore = liveScore;
        this.eventTimestamp = eventTimestamp;
    }

    public LiveScore getLiveScore() {
        return liveScore;
    }

    public Long getEventTimestamp() {
        return eventTimestamp;
    }
}
