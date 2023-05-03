package io.mwi.traintracker.digitrafic;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DigitrafficTrainLocation(String trainNumber, String departureDate, LocalDateTime timestamp, Location location, BigDecimal speed) {

    public record Location(String type, List<BigDecimal> coordinates){}

}
