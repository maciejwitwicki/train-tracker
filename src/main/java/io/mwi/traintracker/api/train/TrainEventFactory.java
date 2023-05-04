package io.mwi.traintracker.api.train;

import org.springframework.stereotype.Component;

@Component
class TrainEventFactory {

    private static final String KEEP_ALIVE_EVENT_TYPE = "KEEP_ALIVE";
    private static final String TRAIN_LOCATION_EVENT_TYPE = "TRAIN_LOCATION";

    SSEvent<TrainLocation> createKeepAliveEvent() {
        return new SSEvent<>(KEEP_ALIVE_EVENT_TYPE, null);
    }

    SSEvent<TrainLocation> createTrainLocationEvent(TrainLocation location) {
        return new SSEvent<>(TRAIN_LOCATION_EVENT_TYPE, location);
    }
}
