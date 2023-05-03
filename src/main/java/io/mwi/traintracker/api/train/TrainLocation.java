package io.mwi.traintracker.api.train;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TrainLocation(String id, String name, String destination, BigDecimal speed, LatLon coordinates) {

    public record LatLon(BigDecimal lat, BigDecimal lon) {
    }

}
