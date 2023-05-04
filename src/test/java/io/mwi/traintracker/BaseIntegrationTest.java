package io.mwi.traintracker;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class BaseIntegrationTest {

    @Container
    private static final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:21.1.0")
            .withRealmImportFile("/keycloak-realm.json");

    @DynamicPropertySource
    private static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        var authServerUrl = keycloak.getAuthServerUrl();
        var issuerUri = authServerUrl + "realms/master";
        registry.add("spring.security.oauth2.client.provider.keycloak-registration.issuer-uri", () -> issuerUri);
        registry.add("spring.security.oauth2.resource-server.jwt.issuer-uri", () -> issuerUri);
        registry.add("io.mwi.keycloak.url", () -> authServerUrl);
    }
}
