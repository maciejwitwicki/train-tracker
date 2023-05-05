package io.mwi.traintracker.api.login;

import jakarta.validation.constraints.NotBlank;

record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
