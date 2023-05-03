package io.mwi.traintracker.api.train;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TrainLocation(String id, String name, String destination, BigDecimal speed, LonLat coordinates) {

    public record LonLat(BigDecimal lon, BigDecimal lat) {
    }

}
