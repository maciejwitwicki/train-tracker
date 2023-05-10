package io.mwi.traintracker.api.user;


import jakarta.validation.constraints.NotBlank;

record CreateUserRequest(
        @NotBlank String firstname,
        @NotBlank String lastname,
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password) {
}
