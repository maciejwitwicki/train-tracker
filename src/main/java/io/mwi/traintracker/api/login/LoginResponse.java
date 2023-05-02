package io.mwi.traintracker.api.login;

public record LoginResponse(String accessToken, int expiresInSeconds) {
}
