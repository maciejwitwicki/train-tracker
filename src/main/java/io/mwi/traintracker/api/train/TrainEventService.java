package io.mwi.traintracker.api.train;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Slf4j
@Service
public class TrainEventService {

    private final Sinks.Many<SSEvent<TrainLocation>> trains = Sinks.many()
            .multicast().directBestEffort();

    private final Flux<SSEvent<TrainLocation>> keepAlive = Flux.interval(Duration.ofSeconds(10))
            .map(i -> new SSEvent<>("KEEP_ALIVE", null));

    public void updateTrainLocation(TrainLocation location) {
        trains.tryEmitNext(new SSEvent<>("TRAIN_LOCATION", location));
    }

    Flux<SSEvent<TrainLocation>> getEventStream() {
        return Flux.merge(
                trains.asFlux(),
                keepAlive
        ).map(TrainEventService::logEvents);
    }

    private static SSEvent<TrainLocation> logEvents(SSEvent<TrainLocation> event) {
        if (event.value() != null) {
            log.info("SSE: {}", event);
        }
        return event;
    }

}
