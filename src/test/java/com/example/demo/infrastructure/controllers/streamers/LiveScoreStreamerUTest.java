package com.example.demo.infrastructure.controllers.streamers;

import com.example.demo.MockitoExtension;
import com.example.demo.domain.LiveScore;
import com.example.demo.use_cases.GetLiveScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Clock;
import java.time.Duration;

import static java.time.LocalDateTime.of;
import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LiveScoreStreamerUTest {

    private static final Duration ONE_SECOND = Duration.ofMillis(1000);

    private LiveScoreStreamer liveScoreStreamer;

    @Mock
    private GetLiveScore getLiveScore;

    private VirtualTimeScheduler virtualTimeScheduler;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(of(2017, 9, 27, 11, 25, 12).toInstant(UTC), UTC);
        liveScoreStreamer = new LiveScoreStreamer(getLiveScore, fixedClock);
        virtualTimeScheduler = VirtualTimeScheduler.getOrSet();
    }

    @Nested
    class GetLiveShould {

        @Test
        void return_no_event_during_the_first_three_seconds_period() {
            // given
            LiveScore liveScore = mock(LiveScore.class);
            when(getLiveScore.execute()).thenReturn(liveScore);

            // when
            Flux<ServerSentEvent<LiveScoreEvent>> live = liveScoreStreamer.getLive();

            // then
            StepVerifier.withVirtualTime(() -> live, () -> virtualTimeScheduler, Long.MAX_VALUE)
                    .expectSubscription()
                    .expectNoEvent(ONE_SECOND)
                    .thenCancel().verify();
        }

        @Test
        void return_a_flux_of_server_sent_events_containing_live_from_repository_as_data_after_a_three_seconds_period() {
            // given
            LiveScore liveScore = mock(LiveScore.class);
            when(getLiveScore.execute()).thenReturn(liveScore);

            // when
            Flux<ServerSentEvent<LiveScoreEvent>> live = liveScoreStreamer.getLive();

            // then
            StepVerifier.withVirtualTime(() -> live, () -> virtualTimeScheduler, Long.MAX_VALUE)
                    .expectSubscription()
                    .thenAwait(ONE_SECOND)
                    .assertNext(serverSentEvent -> this.assertIsLiveEvent(serverSentEvent, liveScore))
                    .thenCancel().verify();
        }

        private void assertIsLiveEvent(ServerSentEvent<LiveScoreEvent> serverSentEvent, LiveScore liveScore) {
            assertThat(serverSentEvent.data().getLiveScore()).isEqualTo(liveScore);
            assertThat(serverSentEvent.data().getEventTimestamp()).isEqualTo(1506511512000L);
            assertThat(serverSentEvent.event()).isEqualTo("live-score");
            assertThat(serverSentEvent.comment()).isNull();
        }
    }
}
