package com.example.demo.infrastructure.repositories;

import com.example.demo.domain.raw.LiveScoreRaw;
import org.springframework.stereotype.Component;

public class LiveScoreRawImpl implements LiveScoreRaw {

    private final Integer score;

    private LiveScoreRawImpl(Integer score) {
        this.score = score;
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Component
    public static class Factory {
        public LiveScoreRaw create(Integer score) {
            return new LiveScoreRawImpl(score);
        }
    }
}
