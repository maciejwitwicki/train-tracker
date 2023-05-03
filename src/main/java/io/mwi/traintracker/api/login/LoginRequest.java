package io.mwi.traintracker.api.login;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
