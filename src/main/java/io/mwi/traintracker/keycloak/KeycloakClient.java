package io.mwi.traintracker.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class KeycloakClient {

    private final static String USERS_URL = "/users";
    private final static String USERS_BY_ID_URL = "/users/{id}";

    private final WebClient webClient;

    public KeycloakClient(WebClientFactory webClientFactory, KeycloakProperties keycloakProperties) {
        this.webClient = webClientFactory.buildWebClient();
    }

    public UserRepresentation getUserById(String userId) {
        return block(webClient.get()
                .uri(USERS_BY_ID_URL, userId)
                .retrieve()
                .bodyToMono(UserRepresentation.class));

    }

    public UserRepresentation createUser() {
        var creds = new CredentialRepresentation();
        creds.setValue("password");
        var user = new UserRepresentation();
        user.setUsername("user1");
        user.setEnabled(true);
        user.setCredentials(List.of(creds));
        return block(webClient.post()
                .uri(USERS_URL)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(UserRepresentation.class));
    }

    private <T> T block(Mono<T> reactive) {
        try {
            return reactive.toFuture().get();
        } catch (Exception e) {
            throw new KeycloakClientException("Error while retrieving resource", e);
        }
    }

}
