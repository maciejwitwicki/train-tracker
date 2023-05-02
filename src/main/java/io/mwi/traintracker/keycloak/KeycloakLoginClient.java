package io.mwi.traintracker.keycloak;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class KeycloakLoginClient {

    private final WebClient webClient;
    private final KeycloakProperties keycloakProperties;

    public KeycloakLoginClient(WebClientFactory webClientFactory, KeycloakProperties keycloakProperties) {
        this.webClient = webClientFactory.buildLoginWebClient();
        this.keycloakProperties = keycloakProperties;
    }

    public Mono<AccessTokenResponse> getAuthTokenByUserCredentials(String user, String password) {
        var requestBody = BodyInserters.fromFormData("grant_type", "password")
                .with("client_id", keycloakProperties.clientId())
                .with("client_secret", keycloakProperties.clientSecret())
                .with("username", user)
                .with("password", password);

        return webClient.post()
                .body(requestBody)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new KeycloakClientException("Keycloak error (User input exception): " + error.bodyToMono(String.class)))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        error -> Mono.error(new KeycloakClientException("Keycloak error (Server error): " + error.bodyToMono(String.class)))
                )
                .bodyToMono(AccessTokenResponse.class)
                .onErrorMap(error -> {
                    throw new KeycloakClientException("boo");
                });


    }

}
