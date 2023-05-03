package io.mwi.traintracker.api.train;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.time.Duration;

@Slf4j
@Service
public class TrainService {

    private final Sinks.Many<SSEvent<TrainLocation>> trains = Sinks.many()
            .multicast()
            .onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);

    private final Flux<SSEvent<TrainLocation>> keepAlive = Flux.interval(Duration.ofSeconds(10))
            .map(i -> new SSEvent<>("KEEP_ALIVE", null));

    Flux<SSEvent<TrainLocation>> getEventStream() {
        return Flux.merge(
                trains.asFlux(),
                keepAlive
        ).map(TrainService::logEvents);
    }

    void updateTrainLocation(TrainLocation location) {
        trains.tryEmitNext(new SSEvent<>("TRAIN_LOCATION", location));
    }

    private static SSEvent<TrainLocation> logEvents(SSEvent<TrainLocation> event) {
        if (event.value() != null) {
            log.info("SSE: {}", event);
        }
        return event;
    }

}
