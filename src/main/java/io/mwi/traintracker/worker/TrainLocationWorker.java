package io.mwi.traintracker.worker;

import io.mwi.traintracker.api.train.TrainEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainLocationWorker {

    private final WorkerFeatureFlagService workerFeatureFlagService;
    private final TrainLocationService trainLocationService;
    private final TrainEventService trainService;

    @Scheduled(fixedDelay = 5000)
    void queryTrains() {
        int numberOfItemsToGet = workerFeatureFlagService.getFeatureFlagValue();
        if (numberOfItemsToGet == 0) {
            return;
        }

        trainLocationService.getTrainLocations(numberOfItemsToGet)
                .subscribe(trainService::updateTrainLocation);
    }

}
