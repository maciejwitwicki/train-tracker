package io.mwi.traintracker.api.login;

import io.mwi.traintracker.api.ApiClientException;
import io.mwi.traintracker.api.ApiServerException;
import io.mwi.traintracker.keycloak.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static io.mwi.traintracker.TestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccessTokenServiceTest {

    private final static String USER_ID = randomString();
    private final static String PASSWORD = randomString();

    private final KeycloakLoginClient keycloakLoginClient = mock(KeycloakLoginClient.class);
    private final AccessTokenMapper accessTokenMapper = mock(AccessTokenMapper.class);

    private AccessTokenService toTest;

    @BeforeEach
    void setUp() {
        mockMapperResponse();
        toTest = new AccessTokenService(keycloakLoginClient, accessTokenMapper);
    }

    @Test
    void delegateRequestToKeycloakClient() {
        // given
        AccessTokenResponse keycloakResponse = AccessTokenResponseFixture.anAccessTokenResponse();
        when(keycloakLoginClient.getAuthTokenByUserCredentials(any(), any()))
                .thenReturn(Mono.just(keycloakResponse));

        LoginResponse expected = mockMapperResponse();

        // when
        var result = StepVerifier.create(toTest.getAccessTokenByUserCredentials(USER_ID, PASSWORD));

        // then
        result.expectNext(expected)
                .verifyComplete();
    }

    @Test
    void mapsClientExceptionProperly() {
        // given
        mockKeycloakException(new KeycloakClientException("client-exception"));

        // when
        var result = StepVerifier.create(toTest.getAccessTokenByUserCredentials(USER_ID, PASSWORD));

        // then
        result.expectErrorSatisfies(err -> assertThat(err)
                        .isInstanceOf(ApiClientException.class)
                        .hasMessage("Incorrect user input")
                        .hasCauseInstanceOf(KeycloakClientException.class))
                .verify();

    }

    @Test
    void mapsServerExceptionProperly() {
        // given
        mockKeycloakException(new KeycloakServerException("client-exception"));

        // when
        var result = StepVerifier.create(toTest.getAccessTokenByUserCredentials(USER_ID, PASSWORD));

        // then
        result.expectErrorSatisfies(err -> assertThat(err)
                        .isInstanceOf(ApiServerException.class)
                        .hasMessage("Internal server error")
                        .hasCauseInstanceOf(KeycloakServerException.class))
                .verify();

    }

    private LoginResponse mockMapperResponse() {
        LoginResponse response = LoginResponseFixture.aLoginResponse();
        when(accessTokenMapper.mapAccessTokenResponse(any())).thenReturn(response);
        return response;
    }

    private void mockKeycloakException(Throwable error) {
        when(keycloakLoginClient.getAuthTokenByUserCredentials(any(), any()))
                .thenReturn(Mono.error(error));
    }

}