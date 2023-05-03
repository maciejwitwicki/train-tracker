package io.mwi.traintracker.api.train;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/trains")
@RequiredArgsConstructor
class TrainController {

    private final TrainEventService trainService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<SSEvent<TrainLocation>> getTrains() {
        return trainService.getEventStream();
    }

    @PutMapping("/{trainId}/location")
    void updateTrainLocation(@PathVariable String trainId, @RequestBody TrainLocationRequest request) {
        var lonLat = latLonFromList(request.coordinates());
        var location = TrainLocation.builder()
                .id(trainId)
                .name(request.name())
                .destination(request.destination())
                .speed(request.speed())
                .coordinates(lonLat)
                .build();

        trainService.updateTrainLocation(location);
    }

    private TrainLocation.LatLon latLonFromList(List<BigDecimal> coordinates) {
        return new TrainLocation.LatLon(coordinates.get(1), coordinates.get(0));
    }
}
