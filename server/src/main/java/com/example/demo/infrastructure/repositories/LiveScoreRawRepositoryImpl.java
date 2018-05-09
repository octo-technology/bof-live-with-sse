package com.example.demo.infrastructure.repositories;

import com.example.demo.domain.raw.LiveScoreRaw;
import com.example.demo.domain.raw.LiveScoreRawRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LiveScoreRawRepositoryImpl implements LiveScoreRawRepository {

    private final LiveScoreRawImpl.Factory liveScoreRawImplFactory;
    private final ScoreEndpoint scoreEndpoint;

    public LiveScoreRawRepositoryImpl(LiveScoreRawImpl.Factory liveScoreRawImplFactory, ScoreEndpoint scoreEndpoint) {
        this.liveScoreRawImplFactory = liveScoreRawImplFactory;
        this.scoreEndpoint = scoreEndpoint;
    }

    @Override
    public LiveScoreRaw find() {
        Integer randomInteger = scoreEndpoint.generateRandomInteger();
        System.out.println("Score: " + randomInteger);

        return liveScoreRawImplFactory.create(randomInteger);
    }
}
