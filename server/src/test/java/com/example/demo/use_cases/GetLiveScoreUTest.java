package com.example.demo.use_cases;

import com.example.demo.MockitoExtension;
import com.example.demo.domain.LiveScore;
import com.example.demo.domain.raw.LiveScoreRaw;
import com.example.demo.domain.raw.LiveScoreRawRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLiveScoreUTest {

    private GetLiveScore getLiveScore;

    @Mock
    private LiveScoreRawRepository liveScoreRawRepository;

    @BeforeEach
    void setUp() {
        getLiveScore = new GetLiveScore(liveScoreRawRepository);

        LiveScore.factory = mock(LiveScore.Factory.class);
    }

    @Nested
    class ExecuteSould {

        @Test
        void return_live_score() {
            // given
            LiveScoreRaw liveScoreRaw = mock(LiveScoreRaw.class);
            when(liveScoreRawRepository.find()).thenReturn(liveScoreRaw);

            LiveScore expectedLiveScore = mock(LiveScore.class);
            when(LiveScore.factory.create(liveScoreRaw)).thenReturn(expectedLiveScore);

            // when
            LiveScore liveScore = getLiveScore.execute();

            // then
            assertThat(liveScore).isEqualTo(expectedLiveScore);
        }
    }
}
