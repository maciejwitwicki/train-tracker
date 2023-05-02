package io.mwi.traintracker.api.login;

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
                .map(accessTokenMapper::mapAccessTokenResponse);
    }

}
