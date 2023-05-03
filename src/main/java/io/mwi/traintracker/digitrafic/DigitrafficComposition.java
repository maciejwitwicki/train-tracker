package io.mwi.traintracker.digitrafic;

import java.util.List;

public record DigitrafficComposition(
        String trainNumber,
        String operatorShortCode,
        String trainCategory,
        String trainType,
        List<JourneySection> journeySections) {

    public JourneySection getLastSection() {
        if (journeySections.isEmpty()) {
            throw new IllegalArgumentException("Expecting at least one section");
        }
        var size = journeySections.size();
        return journeySections.get(size - 1);
    }


    public record JourneySection(TimeTableRow beginTimeTableRow, TimeTableRow endTimeTableRow) {
    }

    public record TimeTableRow(String stationShortCode) {
    }
}
