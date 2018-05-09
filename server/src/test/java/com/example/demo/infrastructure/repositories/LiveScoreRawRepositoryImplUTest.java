package com.example.demo.infrastructure.repositories;

import com.example.demo.MockitoExtension;
import com.example.demo.domain.raw.LiveScoreRaw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LiveScoreRawRepositoryImplUTest {

    private LiveScoreRawRepositoryImpl liveScoreRawRepositoryImpl;

    @Mock
    private LiveScoreRawImpl.Factory liveScoreRawImplFactory;

    @Mock
    private ScoreEndpoint scoreEndpoint;

    @BeforeEach
    void setUp() {
        liveScoreRawRepositoryImpl = new LiveScoreRawRepositoryImpl(liveScoreRawImplFactory, scoreEndpoint);
    }

    @Nested
    class FindShould {

        @Test
        void return_live_score_raw_built_from_score_endpoint() {
            // given
            Integer scoreFromEndpoint = 5;
            when(scoreEndpoint.generateRandomInteger()).thenReturn(scoreFromEndpoint);

            LiveScoreRaw expectedLiveScoreRaw = mock(LiveScoreRaw.class);
            when(liveScoreRawImplFactory.create(scoreFromEndpoint)).thenReturn(expectedLiveScoreRaw);

            // when
            LiveScoreRaw result = liveScoreRawRepositoryImpl.find();

            // then
            assertThat(result).isEqualTo(expectedLiveScoreRaw);
        }
    }
}
