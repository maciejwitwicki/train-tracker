package io.mwi.traintracker.api.user;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public record CreateUserRequest(
         @NotBlank String username,
         @NotBlank String email,
         @NotBlank String password) {
}
