package io.mwi.traintracker.digitrafic;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
class DigitraficWebClientFactory {

    WebClient createTrainLocationWebClient() {
        return WebClient.builder()
                .baseUrl("https://rata.digitraffic.fi/api")
                .build();
    }

}
