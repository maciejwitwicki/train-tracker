package io.mwi.traintracker.worker;

import io.mwi.traintracker.api.train.TrainLocation;
import io.mwi.traintracker.digitrafic.DigitrafficComposition;
import io.mwi.traintracker.digitrafic.DigitrafficTrainLocation;
import io.mwi.traintracker.digitrafic.DigitrafficTrainStation;
import io.mwi.traintracker.digitrafic.DigitraficService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
class TrainLocationService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    private final Clock clock;
    private final DigitraficService digitraficService;

    Flux<TrainLocation> getTrainLocations(int limit) {
        var departureDate = DATE_FORMATTER.format(LocalDate.now(clock));
        return digitraficService.getTrainLocations()
                .take(limit, true)
                .flatMap(train -> getRouteDetails(train, departureDate));
    }

    private Mono<TrainLocation> getRouteDetails(DigitrafficTrainLocation train, String departureDate) {
        return digitraficService.getTrainComposition(departureDate, train.trainNumber())
                .flatMap(composition -> {
                    var finalSection = composition.getLastSection();
                    var finalStationCode = finalSection.endTimeTableRow().stationShortCode();
                    return getStationByShortCode(finalStationCode)
                            .flatMap(station -> buildTrainLocation(train, composition, station));
                });

    }

    private Mono<DigitrafficTrainStation> getStationByShortCode(String code) {
        return digitraficService.getStations()
                .filter(s -> code.equalsIgnoreCase(s.stationShortCode()))
                .take(1)
                .singleOrEmpty();
    }

    private Mono<TrainLocation> buildTrainLocation(DigitrafficTrainLocation train,
                                                   DigitrafficComposition composition,
                                                   DigitrafficTrainStation station) {
        return Mono.fromCallable(() -> TrainLocation.builder()
                .id(train.trainNumber())
                .name(buildTrainName(composition))
                .speed(train.speed())
                .coordinates(buildCoordinates(train.location().coordinates()))
                .destination(station.stationName())
                .build());
    }

    private String buildTrainName(DigitrafficComposition composition) {
        return "%s %S %s".formatted(composition.operatorShortCode(), composition.trainType(), composition.trainCategory());
    }


    private TrainLocation.LatLon buildCoordinates(List<BigDecimal> coordinates) {
        return new TrainLocation.LatLon(coordinates.get(1), coordinates.get(0));
    }

}
