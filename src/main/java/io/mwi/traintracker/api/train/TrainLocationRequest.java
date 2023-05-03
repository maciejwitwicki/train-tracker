package io.mwi.traintracker.api.train;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@Valid
public record TrainLocationRequest(
        @NotBlank  String name,
        @NotBlank String destination,
        @Positive BigDecimal speed,
        @Size(min = 2, max = 2) List<BigDecimal> coordinates) {
}
