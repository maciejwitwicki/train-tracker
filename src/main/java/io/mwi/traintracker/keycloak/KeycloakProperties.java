package io.mwi.traintracker.keycloak;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Valid
@ConfigurationProperties(prefix = "io.mwi.keycloak")
public record KeycloakProperties(
        @NotBlank String url,
        @NotBlank String realm,
        @NotBlank String clientId,
        @NotBlank String clientSecret,
        @NotNull AdminProperties admin,
        @NotNull SuperUserProperties superUser) {

    record AdminProperties(
            @NotBlank String id,
            @NotBlank String user,
            @NotBlank String password) {
    }

    record SuperUserProperties(
            @NotBlank String id,
            @NotBlank String secret) {
    }

}
