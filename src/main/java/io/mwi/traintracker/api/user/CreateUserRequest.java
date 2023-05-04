package io.mwi.traintracker.api.user;


import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password) {
}
