package io.mwi.traintracker.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class KeycloakAdminClient {

    private final static String USERS_URL = "/users";
    private final static String USERS_BY_ID_URL = "/users/{id}";

    private final WebClient webClient;

    public KeycloakAdminClient(WebClientFactory webClientFactory) {
        this.webClient = webClientFactory.buildAdminWebClient();
    }

    public Mono<UserRepresentation> getUserById(String userId) {
        return webClient.get()
                .uri(USERS_BY_ID_URL, userId)
                .retrieve()
                .bodyToMono(UserRepresentation.class);
    }

    public Mono<String> createUser(String firstname, String lastname, String username, String email, String password) {
        var credentials = new CredentialRepresentation();
        credentials.setType("password");
        credentials.setTemporary(false);
        credentials.setValue(password);
        var user = new UserRepresentation();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUsername(username);
        user.setEnabled(true);
        user.setEmail(email);
        user.setCredentials(List.of(credentials));
        return webClient.post()
                .uri(USERS_URL)
                .bodyValue(user)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::logError)
                .toBodilessEntity()
                .map(this::getIdFromLocationHeader);
    }

    private Mono<Throwable> logError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .map(message -> {
                    log.error("Bad request: {}", message);
                    return new KeycloakClientException(message);
                });
    }

    private String getIdFromLocationHeader(ResponseEntity<Void> entity) {
        var path = entity.getHeaders().getLocation().getPath();
        var lastForwardSlashIndex = path.lastIndexOf('/');
        return path.substring(lastForwardSlashIndex + 1);
    }


}
