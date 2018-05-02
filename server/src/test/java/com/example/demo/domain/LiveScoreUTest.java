package com.example.demo.domain;

import com.example.demo.MockitoExtension;
import com.example.demo.domain.raw.LiveScoreRaw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LiveScoreUTest {

    @BeforeEach
    void setUp() {
        LiveScore.factory = new LiveScore.Factory();
    }

    @Nested
    class FactoryCreateShould {

        @Test
        void bind_scores() {
            // given
            LiveScoreRaw liveScoreRaw = mock(LiveScoreRaw.class);
            when(liveScoreRaw.getScore()).thenReturn(5);

            // when
            LiveScore liveScore = LiveScore.factory.create(liveScoreRaw);

            // then
            assertThat(liveScore.getScore()).isEqualTo(5);
        }
    }
}
