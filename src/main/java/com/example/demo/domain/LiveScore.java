package com.example.demo.domain;

import com.example.demo.domain.raw.LiveScoreRaw;

public class LiveScore {

    public static Factory factory = new Factory();

    private Integer score;

    private LiveScore(LiveScoreRaw liveScoreRaw) {
        score = liveScoreRaw.getScore();
    }

    public Integer getScore() {
        return score;
    }

    public static class Factory {
        public LiveScore create(LiveScoreRaw liveScoreRaw) {
            return new LiveScore(liveScoreRaw);
        }
    }
}
