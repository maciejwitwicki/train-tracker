package io.mwi.traintracker.api;

public class ApiServerException extends RuntimeException {
    public ApiServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
