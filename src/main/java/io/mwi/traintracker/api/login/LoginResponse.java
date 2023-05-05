package io.mwi.traintracker.api.login;

record LoginResponse(String accessToken, int expiresInSeconds) {
}
