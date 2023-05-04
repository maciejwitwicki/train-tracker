package io.mwi.traintracker.api.train;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record TrainLocationRequest(
        @NotBlank String name,
        @NotBlank String destination,
        @Positive BigDecimal speed,
        @Size(min = 2, max = 2) List<BigDecimal> coordinates) {
}
