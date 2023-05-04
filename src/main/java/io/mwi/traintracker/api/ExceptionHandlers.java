package io.mwi.traintracker.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.stream.Stream;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        log.error("Unhandled exception", e);
        return e.getMessage();
    }

    @ExceptionHandler(ApiClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleApiClientException(ApiClientException e) {
        log.warn("Client exception", e);
        return e.getMessage();
    }

    @ExceptionHandler(ApiServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleApiServerException(ApiServerException e) {
        log.warn("Internal server error", e);
        return e.getMessage();
    }

    @ExceptionHandler(WebClientResponseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleWebClientException(WebClientResponseException e) {
        log.error("Web client exception", e);
        return e.getResponseBodyAsString();
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Stream<String> handleKeycloakClientException(WebExchangeBindException e) {
        log.warn("Validation exception", e);
        return e.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError f) {
                        return "%s - %s".formatted(f.getField(), error.getCode());
                    } else {
                        return "%s - %s".formatted(error.getObjectName(), error.getCode());
                    }
                });

    }

}
