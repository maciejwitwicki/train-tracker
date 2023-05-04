package io.mwi.traintracker.api;

public class ApiClientException extends RuntimeException {
    public ApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
