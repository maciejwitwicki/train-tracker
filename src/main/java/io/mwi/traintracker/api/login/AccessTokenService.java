package io.mwi.traintracker.api.login;

import io.mwi.traintracker.api.ApiClientException;
import io.mwi.traintracker.api.ApiServerException;
import io.mwi.traintracker.keycloak.KeycloakClientException;
import io.mwi.traintracker.keycloak.KeycloakLoginClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class AccessTokenService {

    private final KeycloakLoginClient keycloakLoginClient;
    private final AccessTokenMapper accessTokenMapper;

    Mono<LoginResponse> getAccessTokenByUserCredentials(String user, String password) {
        return keycloakLoginClient.getAuthTokenByUserCredentials(user, password)
                .onErrorMap(this::mapError)
                .map(accessTokenMapper::mapAccessTokenResponse);
    }

    private Throwable mapError(Throwable throwable) {
        if (throwable instanceof KeycloakClientException e) {
            return new ApiClientException("Incorrect user input", e);
        } else {
            return new ApiServerException("Internal server error", throwable);
        }
    }

}
