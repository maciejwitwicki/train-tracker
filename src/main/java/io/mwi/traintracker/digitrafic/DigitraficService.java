package io.mwi.traintracker.digitrafic;

import io.mwi.traintracker.config.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Clock;

@Service
public class DigitraficService {

    private static final String LOCATIONS_URL = "/v1/train-locations/latest?bbox=1,1,70,70";
    private static final String COMPOSITIONS_URL = "/v1/compositions/{departureDate}/{trainId}";
    private static final String STATIONS_URL = "/v1/metadata/stations";

    private final Clock clock;
    private final WebClient webClient;

    DigitraficService(Clock clock, DigitraficWebClientFactory factory) {
        webClient = factory.createTrainLocationWebClient();
        this.clock = clock;
    }

    public Flux<DigitrafficTrainLocation> getTrainLocations() {
        return webClient.get()
                .uri(LOCATIONS_URL)
                .retrieve()
                .bodyToFlux(DigitrafficTrainLocation.class);
    }

    @Cacheable(CacheConfig.COMPOSITION_CACHE)
    public Mono<DigitrafficComposition> getTrainComposition(String departureDate, String trainId) {
        return webClient.get()
                .uri(COMPOSITIONS_URL, departureDate, trainId)
                .retrieve()
                .bodyToMono(DigitrafficComposition.class);
    }

    @Cacheable(CacheConfig.STATIONS_CACHE)
    public Flux<DigitrafficTrainStation> getStations() {
        return webClient.get()
                .uri(STATIONS_URL)
                .retrieve()
                .bodyToFlux(DigitrafficTrainStation.class);
    }

}
