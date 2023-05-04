package io.mwi.traintracker.api.login;

import io.mwi.traintracker.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

import static io.mwi.traintracker.TestUtils.randomString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(LoginController.class)
@Import(SecurityConfig.class)
class LoginControllerTest {

    private final static String USER_ID = randomString();
    private final static String PASSWORD = randomString();

    @Autowired
    WebTestClient client;

    @MockBean
    AccessTokenService accessTokenService;

    @MockBean
    ReactiveJwtDecoder reactiveJwtDecoder;

    @Test
    void loginEndpoint() {

        var expected = LoginResponseFixture.aLoginResponse();

        when(accessTokenService.getAccessTokenByUserCredentials(USER_ID, PASSWORD))
                .thenReturn(Mono.just(expected));

        client.post()
                .uri("/login")
                .body(BodyInserters.fromValue(new LoginRequest(USER_ID, PASSWORD)))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectAll(x -> x.expectStatus().isOk()
                        .expectBody(LoginResponse.class)
                        .isEqualTo(expected));

    }

    @ParameterizedTest
    @MethodSource("invalidRequests")
    void loginEndpointValidation(String user, String password) {
        LoginRequest invalidBody = new LoginRequest(user, password);
        client
                .post()
                .uri("/login")
                .body(BodyInserters.fromValue(invalidBody))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    private static Stream<Arguments> invalidRequests() {
        var correctRandomString = randomString();
        var blankString = "";
        return Stream.of(
                Arguments.of(correctRandomString, null),
                Arguments.of(null, correctRandomString),
                Arguments.of(null, null),
                Arguments.of(blankString, correctRandomString),
                Arguments.of(correctRandomString, blankString),
                Arguments.of(blankString, blankString)
        );
    }

}