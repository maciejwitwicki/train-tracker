package io.mwi.traintracker.keycloak;

public class KeycloakClientException extends RuntimeException {

    public KeycloakClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeycloakClientException(String message) {
        super(message);
    }
}
