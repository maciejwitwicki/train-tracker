package io.mwi.traintracker.keycloak;

import io.mwi.traintracker.BaseIntegrationTest;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicReference;

import static io.mwi.traintracker.TestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;

class KeycloakAdminClientIntegrationTest extends BaseIntegrationTest {

    private static final String USERNAME = randomString();
    private static final String FIRSTNAME = randomString();
    private static final String LASTNAME = randomString();
    private static final String PASSWORD = randomString();
    private static final String EMAIL = "test@gmail.com";

    @Autowired
    private KeycloakAdminClient toTest;

    @Test
    void createUser() {
        var result = StepVerifier.create(toTest.createUser(FIRSTNAME, LASTNAME, USERNAME, EMAIL, PASSWORD));

        AtomicReference<String> createdUserId = new AtomicReference<>("");

        result.assertNext(userId -> {
                    assertThat(userId).isNotNull();
                    createdUserId.set(userId);
                })
                .verifyComplete();

        verifyUserCreated(createdUserId.get());
    }

    private void verifyUserCreated(String userId) {
        var expected = new UserRepresentation();
        expected.setId(userId);
        expected.setUsername(USERNAME);
        expected.setEmail(EMAIL);

        StepVerifier.create(toTest.getUserById(userId))
                .assertNext(user -> assertThat(user)
                        .usingRecursiveComparison(
                                RecursiveComparisonConfiguration.builder()
                                        .withComparedFields("id", "username", "email")
                                        .build())
                        .isEqualTo(expected))
                .verifyComplete();
    }

}