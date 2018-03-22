package com.example.demo.infrastructure.repositories;

import com.example.demo.MockitoExtension;
import com.example.demo.domain.raw.LiveScoreRaw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LiveScoreRawImplUTest {

    private LiveScoreRaw liveScoreRaw;

    private Integer expectedScore;

    @BeforeEach
    void setUp() {
        expectedScore = 4;

        liveScoreRaw = new LiveScoreRawImpl.Factory().create(expectedScore);
    }

    @Nested
    class GetScoresShould {

        @Test
        void return_scores() {
            // given

            // when
            Integer score = liveScoreRaw.getScore();

            // then
            assertThat(score).isEqualTo(expectedScore);
        }
    }
}
