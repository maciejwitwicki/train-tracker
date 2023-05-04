package io.mwi.traintracker.api.train;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainEventService {

    private static final int KEEP_ALIVE_INTERVAL_SECONDS = 10;
    private final Sinks.Many<SSEvent<TrainLocation>> trains = buildEventsSink();
    private final Flux<SSEvent<TrainLocation>> keepAlive = buildKeepAliveEmitter();

    private final TrainEventFactory trainEventFactory;

    public void updateTrainLocation(TrainLocation location) {
        trains.tryEmitNext(trainEventFactory.createTrainLocationEvent(location));
    }

    Flux<SSEvent<TrainLocation>> getEventStream() {
        return Flux.merge(
                trains.asFlux(),
                keepAlive
        ).map(TrainEventService::logEvents);
    }

    private static Sinks.Many<SSEvent<TrainLocation>> buildEventsSink() {
        return Sinks.many()
                .multicast().directBestEffort();
    }

    private static SSEvent<TrainLocation> logEvents(SSEvent<TrainLocation> event) {
        if (event.value() != null) {
            log.info("SSE: {}", event);
        }
        return event;
    }

    private Flux<SSEvent<TrainLocation>> buildKeepAliveEmitter() {
        return Flux.interval(Duration.ofSeconds(KEEP_ALIVE_INTERVAL_SECONDS))
                .map(i -> trainEventFactory.createKeepAliveEvent());
    }

}
