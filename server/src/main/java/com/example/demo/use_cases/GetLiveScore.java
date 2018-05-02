package com.example.demo.use_cases;

import com.example.demo.domain.LiveScore;
import com.example.demo.domain.raw.LiveScoreRaw;
import com.example.demo.domain.raw.LiveScoreRawRepository;
import org.springframework.stereotype.Service;

@Service
public class GetLiveScore {

    private final LiveScoreRawRepository liveScoreRawRepository;

    public GetLiveScore(LiveScoreRawRepository liveScoreRawRepository) {
        this.liveScoreRawRepository = liveScoreRawRepository;
    }

    public LiveScore execute() {
        LiveScoreRaw liveScoreRaw = liveScoreRawRepository.find();

        return LiveScore.factory.create(liveScoreRaw);
    }
}
