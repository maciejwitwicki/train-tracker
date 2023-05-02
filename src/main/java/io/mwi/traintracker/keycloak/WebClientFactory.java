package io.mwi.traintracker.keycloak;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
class WebClientFactory {

    private static final String KEYCLOAK_REGISTRATION = "keycloak-registration";

    private final ReactiveClientRegistrationRepository reactiveClientRegistrationRepository;
    private final KeycloakProperties keycloakProperties;

    WebClient buildWebClient() {
        var oauth = buildOauthFilter();

        return WebClient.builder()
                .baseUrl("%s/admin/realms/%s".formatted(keycloakProperties.url(), keycloakProperties.realm()))
                .filter(oauth)
                .build();
    }


    private ServerOAuth2AuthorizedClientExchangeFilterFunction buildOauthFilter() {
        var clientService = new InMemoryReactiveOAuth2AuthorizedClientService(reactiveClientRegistrationRepository);
        var authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(reactiveClientRegistrationRepository, clientService);

        var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId(KEYCLOAK_REGISTRATION);

        return oauth;
    }

}
